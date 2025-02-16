package com.currency_crypto_rates.rates.contoller;

import com.currency_crypto_rates.rates.model.CurrencyResponse;
import com.currency_crypto_rates.rates.service.CryptoService;
import com.currency_crypto_rates.rates.service.FiatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyController {


    private final CryptoService cryptoService;
    private final FiatService fiatService;

    @GetMapping("/currency-rates")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CurrencyResponse> fetchCurrencyRates () {

        return Mono.zip(
            fiatService.getFiatRates(),
            cryptoService.getCryptoRates(),
            CurrencyResponse::new);
    }

}
