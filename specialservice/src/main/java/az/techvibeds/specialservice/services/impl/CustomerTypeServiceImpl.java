package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerTypeServiceImpl implements CustomerTypeService {
    private final ModelMapper modelMapper;
}
