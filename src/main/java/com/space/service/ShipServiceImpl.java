package com.space.service;

import com.space.exceptions.ShipNotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.support.RatingCalculator;
import com.space.support.Rounder;
import com.space.support.ShipChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {
    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> findAllShips(Specification<Ship> specification) {
        return shipRepository.findAll(specification);
    }

    @Override
    public Page<Ship> findAllShips(Specification<Ship> specification, Pageable pageable) {
        return shipRepository.findAll(specification, pageable);
    }

    @Override
    public Ship createShip(Ship ship) {
        ShipChecker.checkCreatedShip(ship);
        RatingCalculator.assignRating(ship);
        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public Ship editShip(Long id, Ship ship) {
        ShipChecker.checkShipParameters(ship);
        if (!shipRepository.existsById(id)) throw new ShipNotFoundException("Cannot find a ship to edit");
        Ship updated = updateShip(id, ship);
        return shipRepository.saveAndFlush(updated);
    }

    private Ship updateShip(Long id, Ship ship) {
        Ship changed = shipRepository.findById(id).get();

        if (ship.getName() != null) changed.setName(ship.getName());
        if (ship.getPlanet() != null) changed.setPlanet(ship.getPlanet());
        if (ship.getShipType() != null) changed.setShipType(ship.getShipType());
        if (ship.getProdDate() != null) changed.setProdDate(ship.getProdDate());
        if (ship.getSpeed() != null) changed.setSpeed(ship.getSpeed());
        if (ship.isUsed() != null) changed.setUsed(ship.isUsed());
        if (ship.getCrewSize() != null) changed.setCrewSize(ship.getCrewSize());

        RatingCalculator.assignRating(changed);

        return changed;
    }

    @Override
    public void deleteShip(Long id) {
        if (!shipRepository.existsById(id)) throw new ShipNotFoundException("Cannot find a ship to delete");
        shipRepository.deleteById(id);
    }

    @Override
    public Ship getShip(Long id) {
        if (!shipRepository.existsById(id)) throw new ShipNotFoundException("Ship not found");
        return shipRepository.findById(id).get();
    }

    @Override
    public Integer getShipsCount(Specification<Ship> specification) {
        return shipRepository.findAll(specification).size();
    }

    @Override
    public Specification<Ship> selectByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByPlanet(String planet) {
        return (root, query, criteriaBuilder) -> {
            if (planet != null) {
                return criteriaBuilder.like(root.get("planet"), "%" + planet + "%");
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByShipType(ShipType shipType) {
        return (root, query, criteriaBuilder) -> {
            if (shipType != null) {
                return criteriaBuilder.equal(root.get("shipType"), shipType);
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByProdDate(Long after, Long before) {
//        Long yearInMillis = 1000 * 60 * 60 * 24 * 365L;
        return (root, query, criteriaBuilder) -> {
            if (after != null && before != null) {
                return criteriaBuilder.between(root.get("prodDate"), new Date(after), new Date(before));
            } else if (after != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after));
            } else if (before != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), new Date(before));
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByUse(Boolean isUsed) {
        return (root, query, criteriaBuilder) -> {
            if (isUsed == null) {
                return null;
            } else if (isUsed) {
                return criteriaBuilder.isTrue(root.get("isUsed"));
            }
            return criteriaBuilder.isFalse(root.get("isUsed"));
        };
    }

    @Override
    public Specification<Ship> selectBySpeed(Double minSpeed, Double maxSpeed) {
        return (root, query, criteriaBuilder) -> {
            if (minSpeed != null && maxSpeed != null) {
                return criteriaBuilder.between(root.get("speed")
                        , Rounder.roundHalfUp(minSpeed)
                        , Rounder.roundHalfUp(maxSpeed));
            } else if (minSpeed != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), Rounder.roundHalfUp(minSpeed));
            } else if (maxSpeed != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), Rounder.roundHalfUp(maxSpeed));
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        return (root, query, criteriaBuilder) -> {
            if (minCrewSize != null && maxCrewSize != null) {
                return criteriaBuilder.between(root.get("crewSize"), minCrewSize, maxCrewSize);
            } else if (minCrewSize != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize);
            } else if (maxCrewSize != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize);
            }
            return null;
        };
    }

    @Override
    public Specification<Ship> selectByRating(Double minRating, Double maxRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating != null && maxRating != null) {
                return criteriaBuilder.between(root.get("rating")
                        , Rounder.roundHalfUp(minRating)
                        , Rounder.roundHalfUp(maxRating));
            } else if (minRating != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), Rounder.roundHalfUp(minRating));
            } else if (maxRating != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), Rounder.roundHalfUp(maxRating));
            }
            return null;
        };
    }

}
