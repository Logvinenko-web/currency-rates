package com.currency_crypto_rates.rates.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiatResponse {

    private String currency;
    private double rate;
}
