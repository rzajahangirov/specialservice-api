package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
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
}
