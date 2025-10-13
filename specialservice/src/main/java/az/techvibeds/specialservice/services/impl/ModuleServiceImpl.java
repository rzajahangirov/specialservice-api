package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModelMapper modelMapper;
}
