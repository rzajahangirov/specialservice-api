package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ModelMapper modelMapper;
}
