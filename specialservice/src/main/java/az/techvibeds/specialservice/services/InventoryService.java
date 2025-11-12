package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.inventory.ProductStatusDto;

public interface InventoryService {
    InventoryDto getInventory(String name);

    ProductStatusDto getProductStatuses();
}
