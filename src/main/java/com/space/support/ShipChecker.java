package com.space.support;

import com.space.exceptions.BadShipRequestException;
import com.space.model.Ship;

import java.sql.Date;
import java.time.LocalDate;

public class ShipChecker {
    private static final int MIN_SHIP_NAME_LENGTH = 1;
    private static final int MAX_SHIP_NAME_LENGTH = 50;
    private static final int MIN_PLANET_NAME_LENGTH = MIN_SHIP_NAME_LENGTH;
    private static final int MAX_PLANET_NAME_LENGTH = MAX_SHIP_NAME_LENGTH;
    private static final int MIN_PRODUCTION_YEAR = 2800;
    private static final int MAX_PRODUCTION_YEAR = 3019;
    private static final double MIN_SPEED = 0.01;
    private static final double MAX_SPEED = 0.99;
    private static final int MIN_CREW_SIZE = 1;
    private static final int MAX_CREW_SIZE = 9999;

    public static void checkCreatedShip(Ship ship) {
        checkParametersForNull(ship);
        checkShipParameters(ship);
        checkIsUsed(ship);
    }

    public static void checkParametersForNull(Ship ship) {
        if (ship.getName() == null) throw new BadShipRequestException("Ship name is blank");
        if (ship.getPlanet() == null) throw new BadShipRequestException("Ship name is blank");
        if (ship.getShipType() == null) throw new BadShipRequestException("Ship type is blank");
        if (ship.getProdDate() == null) throw new BadShipRequestException("Production date is blank");
        if (ship.getCrewSize() == null) throw new BadShipRequestException("Crew size is blank");
        if (ship.getSpeed() == null) throw new BadShipRequestException("Speed is blank");
    }

    public static void checkShipParameters(Ship ship) {
        checkName(ship.getName());
        checkPlanet(ship.getPlanet());
        checkProdDate(ship.getProdDate());
        checkSpeed(ship.getSpeed());
        checkCrewSize(ship.getCrewSize());
    }

    public static void checkIsUsed(Ship ship) {
        if (ship.isUsed() == null) {
            ship.setUsed(false);
        }
    }

    public static Long obtainValidID(String id) {
        if (id == null || id.isEmpty()) throw new BadShipRequestException("ID is blank");
        Long parsed = parse(id);
        if (parsed < 1) throw new BadShipRequestException("Incorrect ID. ID must be greater than zero");
        return parsed;
    }

    private static Long parse(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            throw new BadShipRequestException("Incorrect ID", nfe);
        }
    }

    private static void checkName(String name) {
        if (name != null) {
            if (name.length() < MIN_SHIP_NAME_LENGTH || name.length() > MAX_SHIP_NAME_LENGTH) {
                throw new BadShipRequestException("Ship name is too long or blank");
            }
        }
    }

    private static void checkPlanet(String planet) {
        if (planet != null) {
            if (planet.length() < MIN_PLANET_NAME_LENGTH || planet.length() > MAX_PLANET_NAME_LENGTH) {
                throw new BadShipRequestException("Ship name is too long or blank");
            }
        }
    }

    private static void checkProdDate(Date prodDate) {
        if (prodDate != null) {
            if (prodDate.getTime() < 0) throw new BadShipRequestException("Prod date is less than zero");
            LocalDate localDate = prodDate.toLocalDate();
            if (localDate.getYear() < MIN_PRODUCTION_YEAR || localDate.getYear() > MAX_PRODUCTION_YEAR) {
                throw new BadShipRequestException("Prod date is out of range");
            }
        }
    }

    private static void checkCrewSize(Integer crewSize) {
        if (crewSize != null) {
            if (crewSize < MIN_CREW_SIZE || crewSize > MAX_CREW_SIZE) {
                throw new BadShipRequestException("Crew size is out of range");
            }
        }
    }

    private static void checkSpeed(Double speed) {
        if (speed != null) {
            double roundedSpeed = Rounder.roundHalfUp(speed);
            if (roundedSpeed < MIN_SPEED || roundedSpeed > MAX_SPEED) {
                throw new BadShipRequestException("Speed is out of range");
            }
        }
    }
}
