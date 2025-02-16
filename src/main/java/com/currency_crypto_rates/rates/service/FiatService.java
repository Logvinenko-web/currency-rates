package com.currency_crypto_rates.rates.service;

import com.currency_crypto_rates.rates.config.BuildConfig;
import com.currency_crypto_rates.rates.model.FiatDto;
import com.currency_crypto_rates.rates.entity.Fiat;
import com.currency_crypto_rates.rates.model.FiatResponse;
import com.currency_crypto_rates.rates.repository.FiatCurrencyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class FiatService {

    private final WebClient webClient;
    private final FiatCurrencyRepository repository;

    public Flux<Fiat> fetchAndSaveFiatRates () {
        log.info("Received request to fetch fiat rates");
        return webClient.get()
            .uri(BuildConfig.CONFIG.fiatPath())
            .header("X-API-KEY", BuildConfig.CONFIG.apiKey())
            .retrieve()
            .bodyToFlux(FiatResponse.class)
            .doOnNext(fiat -> log.info("Received fiat data: {}", fiat))
            .map(resp -> new Fiat(null, resp.getCurrency(), resp.getRate(), null))
            .onErrorResume(e -> {
                log.info("Error while fetching fiat data: {}", e.getMessage());
                return repository.findTop3ByOrderByCreatedAtDesc();
            })
            .flatMap(repository::save);
    }

    public Mono<List<FiatDto>> getFiatRates () {

        return fetchAndSaveFiatRates()
            .collectList()
            .defaultIfEmpty(List.of())
            .map(fiatList -> fiatList.stream()
                .map(f -> new FiatDto(f.getCurrency(), f.getRate()))
                .collect(Collectors.toList()));
    }
}
