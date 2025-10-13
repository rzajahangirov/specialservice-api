package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final ModelMapper modelMapper;
}
