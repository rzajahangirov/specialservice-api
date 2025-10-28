package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import az.techvibeds.specialservice.models.Unit;

import java.util.List;

public interface UnitService {
    Unit findUnitById(Long unitId);

    List<UnitGetDto> getAllUnits();
}
