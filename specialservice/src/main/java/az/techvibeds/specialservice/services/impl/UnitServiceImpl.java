package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.unit.UnitCreateDto;
import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import az.techvibeds.specialservice.dtos.unit.UnitReadDto;
import az.techvibeds.specialservice.dtos.unit.UnitUpdateDto;
import az.techvibeds.specialservice.models.Unit;
import az.techvibeds.specialservice.repositories.UnitRepository;
import az.techvibeds.specialservice.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;

    @Override
    public Unit findUnitById(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(()-> new RuntimeException("Unit not found"));
    }

    @Override
    public List<UnitGetDto> getAllUnits() {
        return unitRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UnitGetDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public UnitReadDto create(UnitCreateDto dto) {
        Unit unit = new Unit();
        unit.setName(dto.getName());
        unitRepository.save(unit);
        return modelMapper.map(unit, UnitReadDto.class);
    }

    @Override
    public UnitReadDto update(UnitUpdateDto dto) {
        Unit unit = unitRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        unit.setName(dto.getName());
        unitRepository.save(unit);
        return modelMapper.map(unit, UnitReadDto.class);
    }

    @Override
    public void delete(Long id) {
        unitRepository.deleteById(id);
    }

    @Override
    public UnitReadDto getById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        return modelMapper.map(unit, UnitReadDto.class);
    }

    @Override
    public List<UnitReadDto> getAll() {
        return unitRepository.findAll()
                .stream()
                .map(unit -> modelMapper.map(unit, UnitReadDto.class))
                .collect(Collectors.toList());
    }
}
