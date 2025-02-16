package com.currency_crypto_rates.rates.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("crypto")
public class Crypto {

    @Id
    private Long id;
    @JsonProperty("name")
    private String currency;
    @JsonProperty("value")
    private double rate;
    private LocalDateTime created_at;

}
