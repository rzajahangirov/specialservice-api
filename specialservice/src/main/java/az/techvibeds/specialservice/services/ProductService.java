package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.product.ProductCreateDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.models.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Long calculateCompanyStock(Long companyId);

    Integer getProductCountByCompany(Long companyId);

    List<ProductInventoryDto> getProductsByCompanyId(Long companyId);

    void uploadProductsFromExcel(MultipartFile file, Company company) throws Exception;

    ProductCreateDto createProductAndInventoryRecord(ProductCreateDto productCreateDto, Company byUserEmail) throws Exception;
}
