package com.space.support;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    private static final int DEFAULT_ROUNDING_SCALE = 2;

    public static Double roundHalfUp(Double number) {
        return BigDecimal.valueOf(number)
                .setScale(DEFAULT_ROUNDING_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static Double roundHalfUp(Double number, int roundingScale) {
        return BigDecimal.valueOf(number)
                .setScale(roundingScale, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
