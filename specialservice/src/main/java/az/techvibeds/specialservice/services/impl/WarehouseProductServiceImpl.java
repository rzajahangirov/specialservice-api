package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.repositories.WarehouseProductRepository;
import az.techvibeds.specialservice.services.WarehouseProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseProductServiceImpl implements WarehouseProductService {
    private final WarehouseProductRepository warehouseProductRepository;
}
