package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.repositories.WarehouseActivityRepository;
import az.techvibeds.specialservice.services.WarehouseActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseActivityServiceImpl implements WarehouseActivityService {
    private final WarehouseActivityRepository warehouseActivityRepository;

}
