package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    List<Ship> findAllShips(Specification<Ship> specification);
    Page<Ship> findAllShips(Specification<Ship> specification, Pageable pageable);
    Ship createShip(Ship ship);
    Ship editShip(Long id, Ship ship);
    void deleteShip(Long id);
    Ship getShip(Long id);
    Integer getShipsCount(Specification<Ship> specification);

    Specification<Ship> selectByName(String name);
    Specification<Ship> selectByPlanet(String planet);
    Specification<Ship> selectByShipType(ShipType shipType);
    Specification<Ship> selectByProdDate(Long after, Long before);
    Specification<Ship> selectByUse(Boolean isUsed);
    Specification<Ship> selectBySpeed(Double minSpeed, Double maxSpeed);
    Specification<Ship> selectByCrewSize(Integer minCrewSize, Integer maxCrewSize);
    Specification<Ship> selectByRating(Double minRating, Double maxRating);

}
