package top.yangcc.core;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import top.yangcc.facade.ElasticSearchFacade;
import top.yangcc.facade.dto.EsIndexRequest;
import top.yangcc.facade.dto.EsSearchRequest;
import top.yangcc.facade.result.ComplexAggResult;
import top.yangcc.facade.result.Data.PlainResult;
import top.yangcc.facade.result.Data.ResultCode;
import top.yangcc.facade.result.SearchPageResult;
import top.yangcc.facade.result.SearchResultValue;
import top.yangcc.facade.util.*;
import top.yangcc.facade.util.agg.DateHistogramAgg;
import top.yangcc.facade.util.agg.RangeAgg;
import top.yangcc.facade.util.agg.TermsAgg;
import top.yangcc.facade.util.filter.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ElasticSearchFacadeImpl implements ElasticSearchFacade {

    public static final String ES_INDEX_TIME = "es_index_time";

    public static final String ES_INDEX_TIME_LONG = "es_index_time_long";

    public static final String ES_INDEX_TIME_DATE = "new_index_date_time";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH::mm:ss";
    public static final String OS_CREATE_TIME = "os_create_time";
    public static final int ES_ERR_STATUS = 400;

    @Setter
    private EsClientPoolConfig clientPool;

    @Override
    public SearchPageResult<SearchResultValue> search(EsSearchRequest req) {
        String bizTypeId = clientPool.getSearchProperties().getBizTypeId();
        String indexType = clientPool.getSearchProperties().getIndexType();
        String searchAlias = clientPool.getSearchProperties().getSearchAlias();

        SearchPageResult<SearchResultValue> pageResult;
        try {

            Integer num = req.getSize() == 0 ? 0 : req.getFrom() / req.getSize() + 1;

            pageResult = SearchPageResult.buildSuccessPageResult(req.getSize(), num, new ArrayList<>());

            List<EsFilter> filterList = req.getFilterList();

            BoolQueryBuilder totalQuery = QueryBuilders.boolQuery();

            BoolQueryBuilder totalFilter = QueryBuilders.boolQuery();


            if (CollectionUtils.isNotEmpty(filterList)) {
                totalFilter.filter(buildFilters(filterList));
                totalQuery.filter(totalQuery);
            }

            if (CollectionUtils.isNotEmpty(req.getQueryList())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                for (EsQuery query : req.getQueryList()) {
                    queryBuilder.should(QueryBuilders.matchQuery(query.getName(), query.getValue())
                            .boost(query.getBoost() == null ? 1f : query.getBoost())
                            .operator((query.getOperator() == null || query.getOperator() == EsQuery.Operator.AND)
                                    ? Operator.AND
                                    : Operator.OR));
                }
                totalQuery.mustNot(queryBuilder);
            }
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.searchType(SearchType.QUERY_THEN_FETCH).indices(searchAlias).types(indexType);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchRequest.source(searchSourceBuilder);
            if (CollectionUtils.isNotEmpty(req.getCustomScripts())) {
                searchSourceBuilder.query(totalQuery);
            } else {
                FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders =
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[req.getCustomScripts().size()];
                int i = 0;
                for (CustomScript customScript : req.getCustomScripts()) {
                    filterFunctionBuilders[i++] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            buildFilters(customScript.getFilters()),
                            new ScriptScoreFunctionBuilder(new Script(Script.DEFAULT_SCRIPT_TYPE, Script.DEFAULT_SCRIPT_LANG,
                                    customScript.getScript(),
                                    customScript.getParams() == null ? new HashMap<>() : customScript.getParams())));
                }
                FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(totalQuery, filterFunctionBuilders);
                functionScoreQueryBuilder.boostMode(CombineFunction.REPLACE);
                functionScoreQueryBuilder.scoreMode(FunctionScoreQuery.ScoreMode.SUM);
                searchSourceBuilder.query(functionScoreQueryBuilder);
            }

            searchSourceBuilder.from(req.getFrom()).size(req.getSize())
                    .timeout(TimeValue.timeValueMillis(2000));

            if (CollectionUtils.isNotEmpty(req.getSorts())) {
                for (EsSort sort : req.getSorts()) {
                    if ("_sort".equals(sort.getField())) {
                        searchSourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.valueOf(sort.getOrder().name())));
                    } else {
                        searchSourceBuilder.sort(SortBuilders.fieldSort(sort.getField())
                                .order(SortOrder.valueOf(sort.getOrder().name())));
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(req.getAggs())) {
                buildAggregation(searchSourceBuilder, req.getAggs());
            }

            searchSourceBuilder
                    .fetchSource(req.getReturnFields().toArray(new String[req.getReturnFields().size()]), null);

            if (req.getDebug() != null && req.getDebug()) {
                searchSourceBuilder.explain(true);
            }
            SearchResponse searchResponse = clientPool.getClient(bizTypeId).search(searchRequest);
            SearchHits hits = searchResponse.getHits();

            List<SearchResultValue> issueValues = new ArrayList<>();
            for (SearchHit hit : hits.getHits()) {
                SearchResultValue issueValue = new SearchResultValue();
                Map<String, Object> source = hit.getSourceAsMap();
                issueValue.setDocFields(source);

                issueValue.set_id(hit.getId());
                issueValue.setScore(hit.getScore());
                issueValues.add(issueValue);
            }
            Map<String, Map<String, Long>> aggResult = null;
            Map<String, List<SearchResultValue>> tophistDocs = new HashMap<>();
            List<ComplexAggResult> complexAggResults = null;

            if (searchResponse.getAggregations() != null) {
                aggResult = new HashMap<>();
                complexAggResults = new ArrayList<>();
                parseAggResult(aggResult, searchResponse.getAggregations().asList(), tophistDocs, bizTypeId);
            }
            pageResult.setComplexAggResults(complexAggResults);
            pageResult.setAggResult(aggResult);
            pageResult.setData(issueValues);
            pageResult.setTotal(hits.totalHits);
            pageResult.setRequestId(req.getRequestId());
            pageResult.setTopHitAggDocs(tophistDocs);
            pageResult.setSuccess(true);
        } catch (IOException e) {
            pageResult = SearchPageResult.buildFailPageResult(req.getSize(), req.getFrom(), e.getMessage());
        }

        return pageResult;
    }

    @Override
    public PlainResult<String> indexDoc(EsIndexRequest req) {
        String bizTypeId = clientPool.getSearchProperties().getBizTypeId();
        String indexType = clientPool.getSearchProperties().getIndexType();
        String indexName = clientPool.getSearchProperties().getSearchAlias();
        String docId = req.getDocId();

        IndexRequest indexRequest = new IndexRequest(indexName, indexType, docId)
                .timeout(TimeValue.timeValueMillis(2));
        if (null != req.getIssue() && null != req.getIssue().get(OS_CREATE_TIME)) {
            req.getIssue().put(ES_INDEX_TIME, new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date()));
            req.getIssue().put(ES_INDEX_TIME_LONG, req.getIssue().get(OS_CREATE_TIME));
            req.getIssue().put(ES_INDEX_TIME_DATE, req.getIssue().get(OS_CREATE_TIME));
        } else {
            req.getIssue().put(ES_INDEX_TIME, new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(new Date()));
            req.getIssue().put(ES_INDEX_TIME_LONG, System.currentTimeMillis());
            req.getIssue().put(ES_INDEX_TIME_DATE, System.currentTimeMillis());
        }

        indexRequest.source(req.getIssue());

        try {
            IndexResponse indexResponse = clientPool.getClient(bizTypeId).index(indexRequest);
            if (indexResponse.status().getStatus() >= ES_ERR_STATUS) {
                return PlainResult.buildErrResult(ResultCode.EXCEPTION.code, indexResponse.toString());
            }
            return PlainResult.buildSucResult(docId);
        } catch (IOException e) {
            return PlainResult.buildErrResult(ResultCode.EXCEPTION.code, e.getMessage());
        }
    }

    @Override
    public PlainResult<String> updateIndexDoc(EsIndexRequest req) {
        String bizTypeId = clientPool.getSearchProperties().getBizTypeId();
        String indexType = clientPool.getSearchProperties().getIndexType();
        String indexName = clientPool.getSearchProperties().getSearchAlias();
        String docId = req.getDocId();

        UpdateRequest indexRequest = new UpdateRequest(indexName, indexType, docId)
                .timeout(TimeValue.timeValueMillis(2));
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        indexRequest.doc(req.getIssue());


        try {
            UpdateResponse updateResponse = clientPool.getClient(bizTypeId).update(indexRequest);
            if (updateResponse.status().getStatus() >= ES_ERR_STATUS) {
                return PlainResult.buildErrResult(ResultCode.EXCEPTION.code, updateResponse.toString());
            }
            return PlainResult.buildSucResult(docId);
        } catch (IOException e) {
            return PlainResult.buildErrResult(ResultCode.EXCEPTION.code, e.getMessage());
        }
    }

    protected BoolQueryBuilder buildFilters(List<EsFilter> filters) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (EsFilter filter : filters) {
            if (filter == null) {
                continue;
            }
            if (filter instanceof RangeFilter) {
                RangeQueryBuilder rangeQueryBuilder = converterRangeFilter((RangeFilter) filter);
                boolQueryBuilder.filter(rangeQueryBuilder);
            } else if (filter instanceof EqualFilter) {
                boolQueryBuilder.filter(converterEqualFilter((EqualFilter) filter));
            } else if (filter instanceof InFilter) {
                boolQueryBuilder.filter(converterInFilter((InFilter) filter));
            } else if (filter instanceof OrFilter) {
                boolQueryBuilder.filter(converterOrFilter((OrFilter) filter));
            } else if (filter instanceof NotFilter) {
                boolQueryBuilder.filter(converterEqualFilter((NotFilter) filter));
            } else if (filter instanceof NotInFilter) {
                NotInFilter notInFilter = (NotInFilter) filter;
                boolQueryBuilder.mustNot(QueryBuilders.termQuery(notInFilter.getName(), notInFilter.getValue()));
            } else if (filter instanceof ExistFilter) {
                ExistFilter existFilter = (ExistFilter) filter;
                boolQueryBuilder.must(QueryBuilders.existsQuery(existFilter.getField()));
            } else if (filter instanceof NotExistFilter) {
                NotExistFilter notExistFilter = (NotExistFilter) filter;
                boolQueryBuilder.mustNot(QueryBuilders.existsQuery(notExistFilter.getField()));
            } else if (filter instanceof MatchFilter) {
                MatchFilter matchFilter = (MatchFilter) filter;
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchFilter.getName(), matchFilter.getText())
                        .operator(MatchFilter.Operator.AND == matchFilter.getOperator() ? Operator.AND : Operator.OR);
                if (StringUtils.isNotBlank(matchFilter.getMinimumShouldMatch())) {
                    matchQueryBuilder.minimumShouldMatch(matchFilter.getMinimumShouldMatch());
                }
                boolQueryBuilder.must(matchQueryBuilder);
            } else if (filter instanceof NestedFilter) {
                NestedFilter nestedFilter = (NestedFilter) filter;
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(nestedFilter.getPath(), buildFilters(nestedFilter.getFilterList()), ScoreMode.None);
                boolQueryBuilder.must(nestedQueryBuilder);
            }
        }
        return boolQueryBuilder;
    }

    private RangeQueryBuilder converterRangeFilter(RangeFilter rangeFilter) {
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(rangeFilter.getName());
        if (rangeFilter.getDateRange().getFrom() != null) {
            rangeQuery.from(rangeFilter.getDateRange().getFrom());
        }
        if (rangeFilter.getDateRange().getTo() != null) {
            rangeQuery.to(rangeFilter.getDateRange().getTo());
        }
        if (rangeFilter.getDateRange().getGreaterThen() != null) {
            rangeQuery.gte(rangeFilter.getDateRange().getGreaterThen());
        }
        if (rangeFilter.getDateRange().getTo() != null) {
            rangeQuery.lte(rangeFilter.getDateRange().getLessThen());
        }

        Boolean includeLower = rangeFilter.getDateRange().getIncludeFrom();
        Boolean includeMax = rangeFilter.getDateRange().getIncludeTo();
        if (null != includeLower) {
            rangeQuery.includeLower(includeLower);
        }
        if (null != includeMax) {
            rangeQuery.includeUpper(includeMax);
        }
        return rangeQuery;
    }

    private TermQueryBuilder converterEqualFilter(EqualFilter equalFilter) {
        return QueryBuilders.termQuery(equalFilter.getName(), equalFilter.getValue());
    }

    private TermQueryBuilder converterEqualFilter(NotFilter notFilter) {
        return QueryBuilders.termQuery(notFilter.getName(), notFilter.getValue());
    }

    private TermQueryBuilder converterInFilter(InFilter inFilter) {
        return QueryBuilders.termQuery(inFilter.getName(), inFilter.getValues());
    }

    private BoolQueryBuilder converterAndFilter(AndFilter andFilter) {
        return buildFilters(andFilter.getFilterList());
    }

    private BoolQueryBuilder converterOrFilter(OrFilter orFilter) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (EsFilter filter : orFilter.getFilterList()) {
            if (filter == null) {
                continue;
            }
            if (filter instanceof RangeFilter) {
                boolQueryBuilder.should(converterRangeFilter((RangeFilter) filter));
            } else if (filter instanceof EqualFilter) {
                boolQueryBuilder.should(converterEqualFilter((EqualFilter) filter));
            } else if (filter instanceof MatchFilter) {
                MatchFilter matchFilter = (MatchFilter) filter;
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchFilter.getName(), matchFilter.getText())
                        .operator(MatchFilter.Operator.AND == matchFilter.getOperator() ? Operator.AND : Operator.OR);
                if (StringUtils.isNoneBlank(matchFilter.getMinimumShouldMatch())) {
                    matchQueryBuilder.minimumShouldMatch(matchFilter.getMinimumShouldMatch());
                }
                boolQueryBuilder.mustNot(matchQueryBuilder);
            } else if (filter instanceof InFilter) {
                boolQueryBuilder.should(converterInFilter((InFilter) filter));
            } else if (filter instanceof OrFilter) {
                boolQueryBuilder.should(converterOrFilter((OrFilter) filter));
            } else if (filter instanceof AndFilter) {
                boolQueryBuilder.should(converterAndFilter((AndFilter) filter));
            } else if (filter instanceof ExistFilter) {
                boolQueryBuilder.should(QueryBuilders.existsQuery(((ExistFilter) filter).getField()));
            } else if (filter instanceof NotExistFilter) {
                boolQueryBuilder.mustNot(QueryBuilders.existsQuery(((NotExistFilter) filter).getField()));
            } else if (filter instanceof NestedFilter) {
                boolQueryBuilder.should(QueryBuilders.nestedQuery(((NestedFilter) filter).getPath(),
                        buildFilters(((NestedFilter) filter).getFilterList()), ScoreMode.None)
                );
            }
        }
        return boolQueryBuilder;
    }

    protected void buildAggregation(SearchSourceBuilder searchSourceBuilder, List<EsAgg> aggs) {
        if (CollectionUtils.isEmpty(aggs)) {
            return;
        }
        for (EsAgg agg : aggs) {
            if (agg instanceof RangeAgg) {
                RangeAgg rangeAgg = (RangeAgg) agg;
                if (CollectionUtils.isEmpty(rangeAgg.getRanges())) {
                    continue;
                }
                RangeAggregationBuilder rangeAggregationBuilder = AggregationBuilders.range(rangeAgg.getName())
                        .field(rangeAgg.getField());
                for (Range range : rangeAgg.getRanges()) {
                    rangeAggregationBuilder.addRange(range.getFrom(), range.getTo());
                }
                searchSourceBuilder.aggregation(rangeAggregationBuilder);
            } else if (agg instanceof TermsAgg) {
                searchSourceBuilder.aggregation(buildTermAgg((TermsAgg) agg));
            } else if (agg instanceof DateHistogramAgg) {
                DateHistogramAgg histAgg = (DateHistogramAgg) agg;
                DateHistogramAggregationBuilder builder = AggregationBuilders.dateHistogram("dateHistogram").dateHistogramInterval(DateHistogramInterval.DAY)
                        .minDocCount(0).field(histAgg.getField()).format(histAgg.getFormat())
                        .extendedBounds(new ExtendedBounds(histAgg.getBoundStart(), histAgg.getBoundEnd()));
                searchSourceBuilder.aggregation(builder);
            }
        }
    }

    protected AggregationBuilder buildTermAgg(TermsAgg termsAgg) {
        TermsAggregationBuilder termAggBuilder = AggregationBuilders.terms(termsAgg.getName())
                .field(termsAgg.getField());
        if (termsAgg.getSize() != null) {
            termAggBuilder.size(termsAgg.getSize());
        }
        FilterAggregationBuilder filter = null;

        if (CollectionUtils.isNotEmpty(termsAgg.getIncluedValues())) {
            filter = AggregationBuilders.filter("filter_term_" + termsAgg.getName(),
                    QueryBuilders.termsQuery(termsAgg.getField(), termsAgg.getIncluedValues()));
            filter.subAggregation(termAggBuilder);
        }
        if (termsAgg.getHavingSelector() != null) {
            Map<String, String> bucketsPathMap = new HashMap<>();
            bucketsPathMap.put("view_count", "_count");
            String scriptStr = "params.view_count" + termsAgg.getHavingSelector().getCondition().expr + " "
                    + termsAgg.getHavingSelector().getValue();
            Script script = new Script(scriptStr);
            termAggBuilder.subAggregation(PipelineAggregatorBuilders.bucketScript("having", bucketsPathMap, script));
        }
        if (termsAgg.getTopHitAgg() != null && termsAgg.getTopHitAgg().getSize() != null
                && termsAgg.getTopHitAgg().getReturnFields() != null) {
            TopHitsAggregationBuilder topHitsAggregationBuilder = AggregationBuilders.topHits("doc");
            topHitsAggregationBuilder.size(termsAgg.getTopHitAgg().getSize());
            topHitsAggregationBuilder.fetchSource(termsAgg.getTopHitAgg().getReturnFields()
                    .toArray(new String[termsAgg.getTopHitAgg().getReturnFields().size()]), null);

            for (EsSort sort : termsAgg.getTopHitAgg().getSort()) {
                if ("_sort".equals(sort.getField())) {
                    topHitsAggregationBuilder.sort(SortBuilders.fieldSort(sort.getField()).order(SortOrder.valueOf(sort.getOrder().name())));
                }
            }

            termAggBuilder.subAggregation(topHitsAggregationBuilder);
        }

        if (termsAgg.getTermsAgg() != null) {
            termAggBuilder.subAggregation(buildTermAgg(termsAgg.getTermsAgg()));
        }
        return filter != null ? filter : termAggBuilder;
    }

    private void parseAggResult(Map<String, Map<String, Long>> result, List<Aggregation> aggList,
                                Map<String, List<SearchResultValue>> issueValues, String bizTypeId) {

        if (CollectionUtils.isNotEmpty(aggList)) {
            return;
        }

        parseAggResult(result, aggList);

        for (Aggregation aggregation : aggList) {
            if (aggregation instanceof Terms) {
                for (Terms.Bucket bucket : ((Terms) aggregation).getBuckets()) {
                    String key = bucket.getKeyAsString();
                    Aggregations subAggs = bucket.getAggregations();

                    if (subAggs != null && subAggs.get("doc") != null) {
                        List<SearchResultValue> termTopHit = new ArrayList<>();
                        issueValues.put(key, termTopHit);

                        ParsedTopHits topHits = subAggs.get("doc");
                        SearchHits searchHits = topHits.getHits();
                        if (searchHits != null && searchHits.getHits() != null && searchHits.getHits().length > 0) {
                            for (SearchHit searchHit : searchHits.getHits()) {
                                SearchResultValue issueValue = new SearchResultValue();
                                issueValues.get(key).add(issueValue);
                                issueValue.setDocFields(searchHit.getSourceAsMap());
                            }
                        }

                    }
                }
            }
        }


    }

    private void parseAggResult(Map<String, Map<String, Long>> result, List<Aggregation> aggList) {
        if (CollectionUtils.isEmpty(aggList)) {
            return;
        }
        for (Aggregation aggregation : aggList) {
            if (aggregation instanceof Terms) {
                Map<String, Long> termAgg = new HashMap<>();
                for (Terms.Bucket bucket : ((Terms) aggregation).getBuckets()) {
                    if (CollectionUtils.isNotEmpty(bucket.getAggregations().asList())) {
                        termAgg = new HashMap<>();
                        result.put(String.valueOf(bucket.getKey()), termAgg);
                        parseAggResult(result, bucket.getAggregations().asList(), termAgg);
                    } else {
                        termAgg.put(bucket.getKeyAsString(), bucket.getDocCount());
                        result.put(aggregation.getName(), termAgg);
                    }
                }

                if (CollectionUtils.isEmpty(((Terms) aggregation).getBuckets())) {
                    result.put(aggregation.getName(), termAgg);
                }
            } else if (aggregation instanceof org.elasticsearch.search.aggregations.bucket.range.Range) {
                Map<String, Long> rangeAggMap = new HashMap<>();
                org.elasticsearch.search.aggregations.bucket.range.Range rangeAgg = (org.elasticsearch.search.aggregations.bucket.range.Range) aggregation;
                for (org.elasticsearch.search.aggregations.bucket.range.Range.Bucket bucket : rangeAgg.getBuckets()) {
                    rangeAggMap.put(bucket.getKeyAsString(), bucket.getDocCount());
                }
                result.put(aggregation.getName(), rangeAggMap);
            } else if (aggregation instanceof org.elasticsearch.search.aggregations.bucket.filter.Filter) {
                org.elasticsearch.search.aggregations.bucket.filter.Filter filterAgg = (org.elasticsearch.search.aggregations.bucket.filter.Filter) aggregation;
                List<Aggregation> filterSubAggList = filterAgg.getAggregations().asList();
                parseAggResult(result, filterSubAggList);
            } else if (aggregation instanceof Histogram) {
                org.elasticsearch.search.aggregations.bucket.histogram.Histogram histogram = (org.elasticsearch.search.aggregations.bucket.histogram.Histogram) aggregation;
                Map<String, Long> histogramAggMap = new HashMap<>();
                for (org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket bucket : histogram.getBuckets()) {
                    histogramAggMap.put(bucket.getKeyAsString(), bucket.getDocCount());
                }
            }
        }
    }

    private void parseAggResult(Map<String, Map<String, Long>> result, List<Aggregation> aggList,
                                Map<String, Long> aggResult) {
        if (CollectionUtils.isEmpty(aggList)) {
            return;
        }

        for (Aggregation aggregation : aggList) {
            if (aggregation instanceof Terms) {
                Map<String, Long> termAgg = new HashMap<>();

                if (null != aggResult) {
                    termAgg = aggResult;
                }
                for (Terms.Bucket bucket : ((Terms) aggregation).getBuckets()) {
                    if (CollectionUtils.isNotEmpty(bucket.getAggregations().asList())) {
                        result.put(String.valueOf(bucket.getKey()), termAgg);
                        parseAggResult(result, bucket.getAggregations().asList(), termAgg);
                    } else {
                        termAgg.put(bucket.getKeyAsString(), bucket.getDocCount());
                    }
                }

            }
        }

    }
}
