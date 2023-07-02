package com.rova.transactionservice.dals;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long Id;

    @Column(length = 30, nullable = false)
    private String currency;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(length = 5, nullable = false)
    private String countryCode;

    @Column(length = 5, nullable = false)
    private String currencyCode;

    @Column(length = 10, nullable = false)
    private String symbol;
}
