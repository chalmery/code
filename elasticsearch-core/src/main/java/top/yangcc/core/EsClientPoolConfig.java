package top.yangcc.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import top.yangcc.core.configuration.BizSearchProperties;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EsClientPoolConfig {

    @Getter
    @Setter
    private BizSearchProperties searchProperties;

    private final ConcurrentHashMap<String, RestHighLevelClient> clientPool = new ConcurrentHashMap<>();


    public RestHighLevelClient getClient(String bizTypeId){
        return clientPool.get(bizTypeId);
    }


    public void init() {
        try {
            String bizTypeId = searchProperties.getBizTypeId();
            String userName = searchProperties.getSearch().getUserName();
            String password = searchProperties.getSearch().getPassword();
            String host = searchProperties.getSearch().getHost();
            Integer port = searchProperties.getSearch().getPort();

            if (clientPool.containsKey(bizTypeId)) {
                return;
            }

            RestHighLevelClient client = buildClient(host, port, userName, password);

            if (client == null) {
                throw new Exception("inits client pool fail.");
            }
            clientPool.put(bizTypeId, client);
        } catch (Exception e) {
            //日志
        }
    }

    private RestHighLevelClient buildClient(String host, Integer port, String userName, String passowrd) {
        RestHighLevelClient client = null;
        try {
            HttpHost httpHost = new HttpHost(host, port);
            RestClientBuilder builder = RestClient.builder(httpHost);
            final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passowrd));

            builder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
                            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                    .build();

            builder.setRequestConfigCallback(requestBuilder ->
                    requestBuilder.setConnectTimeout(5000).setSocketTimeout(60000));

            client = new RestHighLevelClient(builder);
        } catch (Exception e) {
            //日志
        }
        return client;
    }
}
