package com.currency_crypto_rates.rates.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient () {
        return WebClient.builder().baseUrl(BuildConfig.CONFIG.mockUri()).build();
    }

}
