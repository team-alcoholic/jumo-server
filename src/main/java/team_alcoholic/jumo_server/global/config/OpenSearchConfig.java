package team_alcoholic.jumo_server.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenSearchConfig {

    @Bean
    public OpenSearchClient opensearchClient() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        HttpHost host = new HttpHost("search-jumo-es-test-lhi3i2q4r4o44hunifyo6e3cji.ap-northeast-2.es.amazonaws.com", 443, "https");
        credentialsProvider.setCredentials(
            AuthScope.ANY,
            new UsernamePasswordCredentials("testuser", "testUser1!")
        );

        RestClientBuilder builder = RestClient.builder(host)
            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider);
//                        .setDefaultIOReactorConfig(IOReactorConfig.custom()
//                            .setIoThreadCount(1)
//                            .build());
                }
            });

        RestClient restClient = builder.build();
        return new OpenSearchClient(new RestClientTransport(restClient, new JacksonJsonpMapper(new ObjectMapper())));
    }

}
