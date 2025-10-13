package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final ModelMapper modelMapper;
}
