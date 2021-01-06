package com.space.support;

import com.space.model.Ship;

public class RatingCalculator {
    private static final double MAGIC_COEFFICIENT = 80;
    private static final double THIS_YEAR = 3019;

    public static void assignRating(Ship ship) {
        Double rating = calculate(ship);
        rating = Rounder.roundHalfUp(rating);
        ship.setRating(rating);
    }

    public static Double calculate(Ship ship) {
        double speed = ship.getSpeed();
        double utilizationRate = ship.isUsed() ? 0.5 : 1.0;
        double productionYear = ship.getProdDate().toLocalDate().getYear();

        return (MAGIC_COEFFICIENT * speed * utilizationRate) / (THIS_YEAR - productionYear + 1);
    }

}
