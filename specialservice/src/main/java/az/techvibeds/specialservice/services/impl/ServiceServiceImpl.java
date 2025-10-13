package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ModelMapper modelMapper;
}
