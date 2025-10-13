package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.services.CompanyStockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyStockServiceImpl implements CompanyStockService {
    private final ModelMapper modelMapper;
}
