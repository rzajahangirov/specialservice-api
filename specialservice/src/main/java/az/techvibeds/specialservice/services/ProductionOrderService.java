package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderCreateDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderReadDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderUpdateDto;

import java.security.Principal;
import java.util.List;

public interface ProductionOrderService {
    ProductionOrderReadDto createProductionServiceAndProduct(ProductionOrderCreateDto createDto, String userEmail);

    List<ProductionOrderReadDto> getAllByCompanyId(String name);

    ProductionOrderReadDto updateProductionOrderAndProduct(Long id, ProductionOrderUpdateDto updateDto, String userEmail);

    void deleteById(Long id, Principal principal);

    List<ProductionOrderReadDto> getFilteredProductionOrdersAndProducts(String status, String principal);

    List<ProductionOrderReadDto> getSearchedProductionOrdersAndProducts(String keyword, Principal principal);
}
