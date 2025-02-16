package com.currency_crypto_rates.rates.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config.properties"})
public interface Configuration extends Config {

    @Key("mock.uri")
    String mockUri ();

    @Key("mock.crypto.path")
    String cryptoPath ();

    @Key("mock.fiat.path")
    String fiatPath ();

    @Key("mock.api.key")
    String apiKey ();

}
