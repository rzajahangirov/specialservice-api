package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.unit.UnitCreateDto;
import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import az.techvibeds.specialservice.dtos.unit.UnitReadDto;
import az.techvibeds.specialservice.dtos.unit.UnitUpdateDto;
import az.techvibeds.specialservice.models.Unit;

import java.util.List;

public interface UnitService {
    Unit findUnitById(Long unitId);

    List<UnitGetDto> getAllUnits();

    UnitReadDto create(UnitCreateDto dto);

    UnitReadDto update(UnitUpdateDto dto);

    void delete(Long id);

    UnitReadDto getById(Long id);

    List<UnitReadDto> getAll();
}
