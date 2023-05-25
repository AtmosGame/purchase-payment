package id.ac.ui.cs.advprog.purchasepayment.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Configuration made by
 * <a href="https://medium.com/swlh/spring-boot-webclient-cheat-sheet-5be26cfa3e">Stanislav Vain</a>
 */
@Configuration
@ComponentScan(value = "id.ac.ui.cs.advprog.purchasepayment.config")
public class WebClientConfiguration {
    private URLProperties urlProperties;
    public static final int TIMEOUT = 2000;

    @Bean
    @Qualifier("gamesAppsStore")
    public WebClient webClientWithTimeout() {
        final var httpClient = HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .baseUrl(urlProperties.getGamesAppsStoreURL())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Autowired
    public void setUrlProperties(URLProperties urlProperties) {
        this.urlProperties = urlProperties;
    }
}