package com.currency_crypto_rates.rates.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("fiat ")
public class Fiat {
    @Id
    private Long id;
    private String currency;
    private double rate;
    private LocalDateTime created_at;
}