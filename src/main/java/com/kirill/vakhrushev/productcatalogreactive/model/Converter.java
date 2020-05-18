package com.kirill.vakhrushev.productcatalogreactive.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.util.Pair;

public final class Converter {

    private static final Map<Pair<Currency, Currency>, BigDecimal> CONVERTER_MAP = new HashMap<>();

    static {
        CONVERTER_MAP.put(Pair.of(Currency.USD, Currency.RUB), new BigDecimal("79"));
        CONVERTER_MAP.put(Pair.of(Currency.USD, Currency.EUR), new BigDecimal("0.9"));
        CONVERTER_MAP.put(Pair.of(Currency.EUR, Currency.RUB), new BigDecimal("85"));
        CONVERTER_MAP.put(Pair.of(Currency.EUR, Currency.USD), new BigDecimal("1"));
        CONVERTER_MAP.put(Pair.of(Currency.RUB, Currency.USD), new BigDecimal("0.01"));
        CONVERTER_MAP.put(Pair.of(Currency.RUB, Currency.EUR), new BigDecimal("0.015"));
    }

    private Converter() {
        throw new UnsupportedOperationException();
    }

    public static Product convertProductPrice(Product product, Currency otherCurrency) {
        final BigDecimal otherPrice = convertPrice(product.getPrice(), product.getCurrency(), otherCurrency);
        return new Product(product.getId(), product.getTitle(), otherCurrency, otherPrice);
    }

    public static BigDecimal convertPrice(BigDecimal price, Currency from, Currency to) {
        return price.multiply(CONVERTER_MAP.getOrDefault(Pair.of(from, to), BigDecimal.ONE));
    }
}
