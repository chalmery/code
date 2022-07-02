package top.yangcc.facade;

import top.yangcc.facade.dto.EsIndexRequest;
import top.yangcc.facade.dto.EsSearchRequest;
import top.yangcc.facade.result.Data.PlainResult;
import top.yangcc.facade.result.SearchPageResult;
import top.yangcc.facade.result.SearchResultValue;

public interface ElasticSearchFacade {

    SearchPageResult<SearchResultValue> search(EsSearchRequest req);


    PlainResult<String> indexDoc(EsIndexRequest req);

    PlainResult<String> updateIndexDoc(EsIndexRequest req);

}
