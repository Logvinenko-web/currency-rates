package com.currency_crypto_rates.rates.service;

import com.currency_crypto_rates.rates.config.BuildConfig;
import com.currency_crypto_rates.rates.model.CryptoDto;
import com.currency_crypto_rates.rates.entity.Crypto;
import com.currency_crypto_rates.rates.model.CryptoResponse;
import com.currency_crypto_rates.rates.repository.CryptoCurrencyRepository;
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
public class CryptoService {

    private final WebClient webClient;
    private final CryptoCurrencyRepository repository;

    public Flux<Crypto> fetchAndSaveCryptoRates () {
        log.info("Received request to fetch crypto rates");
        return webClient.get()
            .uri(BuildConfig.CONFIG.cryptoPath())
            .retrieve()
            .bodyToFlux(CryptoResponse.class)
            .doOnNext(crypto -> log.info("Received crypto data: {}", crypto))
            .map(resp -> new Crypto(null, resp.getName(), resp.getValue(), null))
            .onErrorResume(e -> {
                log.info("Error while fetching crypto data: {}", e.getMessage());
                return repository.findTop3ByOrderByCreatedAtDesc();
            })
            .flatMap(repository::save);

    }

    public Mono<List<CryptoDto>> getCryptoRates () {

        return fetchAndSaveCryptoRates()
            .collectList()
            .defaultIfEmpty(List.of())
            .map(cryptoList -> cryptoList.stream()
                .map(c -> new CryptoDto(c.getCurrency(), c.getRate()))
                .collect(Collectors.toList()));
    }

}
