package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.manufacturedproduct.ManufacturedProductCreateDto;
import az.techvibeds.specialservice.dtos.manufacturedproduct.ManufacturedProductUpdateDto;
import az.techvibeds.specialservice.models.ManufacturedProduct;

public interface ManufacturedProductService {
    ManufacturedProduct createProduct(ManufacturedProductCreateDto manufacturedProduct, String userEmail);

}
