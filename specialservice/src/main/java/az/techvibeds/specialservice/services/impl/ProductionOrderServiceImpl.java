package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderCreateDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderReadDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderUpdateDto;
import az.techvibeds.specialservice.enums.ProductionOrderStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.ManufacturedProduct;
import az.techvibeds.specialservice.models.ProductionOrder;
import az.techvibeds.specialservice.repositories.ProductionOrderRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ManufacturedProductService;
import az.techvibeds.specialservice.services.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionOrderServiceImpl implements ProductionOrderService {
    private final ProductionOrderRepository productionOrderRepository;
    private final ManufacturedProductService manufacturedProductService;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    @Override
    public ProductionOrderReadDto createProductionServiceAndProduct(ProductionOrderCreateDto createDto, String userEmail) {
        ProductionOrder productionOrder = modelMapper.map(createDto, ProductionOrder.class);
        productionOrder.setProgressPercentage(0);
        productionOrder.setCompany(companyService.findByUserEmail(userEmail));
        try {
            productionOrder.setStatus(
                    ProductionOrderStatus.valueOf(createDto.getOrderStatus().toUpperCase())
            );
        } catch (Exception e) {
            productionOrder.setStatus(null);
        }
        if (createDto.getQuantity() <= 0){
            throw new RuntimeException("Quantity must be greater than zero");
        }
        ManufacturedProduct manufacturedProduct = manufacturedProductService.createProduct(createDto.getManufacturedProduct(), userEmail);
        productionOrder.setManufacturedProduct(manufacturedProduct);
        productionOrderRepository.save(productionOrder);
        return mapToRead(productionOrder);
    }

    @Override
    public List<ProductionOrderReadDto> getAllByCompanyId(String name) {
        Company company = companyService.findByUserEmail(name);
        List<ProductionOrder> productionOrders = productionOrderRepository.findAllByCompany_Id(company.getId());
        List<ProductionOrderReadDto> productionOrderReadDtos = new ArrayList<>();
        for (ProductionOrder productionOrder : productionOrders) {
            productionOrderReadDtos.add(mapToRead(productionOrder));
        }
        return productionOrderReadDtos;
    }

    @Override
    public ProductionOrderReadDto updateProductionOrderAndProduct(Long id, ProductionOrderUpdateDto updateDto, String userEmail) {
        ProductionOrder productionOrder = productionOrderRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("ProductionOrder not found"));
        if (productionOrder.getCompany() != companyService.findByUserEmail(userEmail)) {
            throw new RuntimeException("Access denied");
        }
        productionOrder.setQuantity(updateDto.getQuantity());
        productionOrder.setStartDate(updateDto.getStartDate());
        productionOrder.setFinishDate(updateDto.getFinishDate());
        productionOrder.setProgressPercentage(updateDto.getProgressPercentage());
        try {
            productionOrder.setStatus(
                    ProductionOrderStatus.valueOf(updateDto.getOrderStatus().toUpperCase())
            );
        } catch (Exception e) {
            productionOrder.setStatus(null);
        }
        if (updateDto.getQuantity() <= 0){
            throw new RuntimeException("Quantity must be greater than zero");
        }
        ManufacturedProduct manufacturedProduct = productionOrder.getManufacturedProduct();
        manufacturedProduct.setName(updateDto.getProduct().getName());
        manufacturedProduct.setDescription(updateDto.getProduct().getDescription());
        productionOrder.setManufacturedProduct(manufacturedProduct);
        productionOrderRepository.save(productionOrder);
        return mapToRead(productionOrder);
    }

    @Override
    public void deleteById(Long id, Principal principal) {
        ProductionOrder productionOrder = productionOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("ProductionOrder not found"));
        if (productionOrder.getCompany() != companyService.findByUserEmail(principal.getName())) {
            throw new RuntimeException("Access denied");
        }
        productionOrderRepository.delete(productionOrder);
    }

    @Override
    public List<ProductionOrderReadDto> getFilteredProductionOrdersAndProducts(String status, String userEmail) {
        ProductionOrderStatus productionOrderStatus;
        switch (status.toUpperCase()) {
            case "PLANNING":
                productionOrderStatus = ProductionOrderStatus.PLANNING;
                break;
            case "IN_PRODUCTION":
                productionOrderStatus = ProductionOrderStatus.IN_PRODUCTION;
                break;
            case "STOPPED":
                productionOrderStatus = ProductionOrderStatus.STOPPED;
                break;
            case "COMPLETED":
                productionOrderStatus = ProductionOrderStatus.COMPLETED;
                break;
            case "PENDING":
                productionOrderStatus = ProductionOrderStatus.PENDING;
                break;
            default:
                throw new RuntimeException("Unknown production order status: " + status);
        }
        Company company = companyService.findByUserEmail(userEmail);
        List<ProductionOrder> productionOrders = productionOrderRepository.findAllByCompany_IdAndStatus(company.getId(), productionOrderStatus);
        List<ProductionOrderReadDto> productionOrderReadDtos = new ArrayList<>();
        for (ProductionOrder productionOrder : productionOrders) {
            productionOrderReadDtos.add(mapToRead(productionOrder));
        }
        return productionOrderReadDtos;
    }

    @Override
    public List<ProductionOrderReadDto> getSearchedProductionOrdersAndProducts(String keyword, Principal principal) {
        Company company = companyService.findByUserEmail(principal.getName());
        List<ProductionOrder> orders = productionOrderRepository
                .searchByKeywordAndCompany(keyword, company.getId());

        return orders.stream()
                .map(this::mapToRead)
                .collect(Collectors.toList());
    }

    private ProductionOrderReadDto mapToRead(ProductionOrder productionOrder) {
        ProductionOrderReadDto dto = modelMapper.map(productionOrder, ProductionOrderReadDto.class);
        dto.setOrderStatus(productionOrder.getStatus().name());
        dto.setProductName(productionOrder.getManufacturedProduct().getName());
        return dto;
    }
}
