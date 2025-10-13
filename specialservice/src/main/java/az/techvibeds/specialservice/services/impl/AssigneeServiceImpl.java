package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.AssigneeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssigneeServiceImpl implements AssigneeService {
    private final ModelMapper modelMapper;
}
