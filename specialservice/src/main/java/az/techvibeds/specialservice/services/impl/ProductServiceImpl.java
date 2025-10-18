package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.ProductRepository;
import az.techvibeds.specialservice.services.CategoryService;
import az.techvibeds.specialservice.services.ProductService;
import az.techvibeds.specialservice.services.WarehouseProductService;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final WarehouseService warehouseService;
    private final CategoryService categoryService;
    private final WarehouseProductService warehouseProductService;

    @Override
    public Long calculateCompanyStock(Long companyId) {
        List<Product> products = productRepository.findAllByCompany_Id(companyId);
        Long stock = 0L;
        for (Product product : products) {
            stock += product.getStock();
        }
        return stock;
    }

    @Override
    public Integer getProductCountByCompany(Long companyId) {
        Integer productCount = productRepository.findAllByCompany_Id(companyId).size();
        return productCount;
    }

    @Override
    public List<ProductInventoryDto> getProductsByCompanyId(Long companyId) {
        List<WarehouseProduct> warehouseProductList = warehouseService.findAllByCompany_Id(companyId);
        List<ProductInventoryDto> productInventoryDtoList =  new ArrayList<>();
        for (WarehouseProduct warehouseProduct : warehouseProductList) {
            ProductInventoryDto productInventoryDto = new ProductInventoryDto();
            Product product = warehouseProduct.getProduct();
            productInventoryDto.setProductCode(product.getProductCode());
            productInventoryDto.setName(product.getName());
            productInventoryDto.setPrice(product.getPrice());
            productInventoryDto.setStock(product.getStock());
            productInventoryDto.setProductStatus(product.getProductStatus().name());
            productInventoryDto.setCategory(product.getCategory().getName());
            productInventoryDto.setWarehouse(warehouseProduct.getWarehouse().getName());
            productInventoryDtoList.add(productInventoryDto);
        }
        return productInventoryDtoList;
    }

    @Override
    public void uploadProductsFromExcel(MultipartFile file, Company company) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Product product = new Product();
            product.setCompany(company);
            product.setName(row.getCell(0).getStringCellValue());
            product.setProductCode(row.getCell(1).getStringCellValue());

            double stockDouble = row.getCell(2).getNumericCellValue();
            Long stock = (long) stockDouble;
            product.setStock(stock);

            BigDecimal price = BigDecimal.valueOf(row.getCell(3).getNumericCellValue());
            product.setPrice(price);

            product.setCategory(categoryService.findByName(row.getCell(4).getStringCellValue()));

            String productStatus = row.getCell(5).getStringCellValue();
            if (productStatus.toUpperCase().equals("IN STOCK") || productStatus.toUpperCase().equals("IN_STOCK")) {
                product.setProductStatus(ProductStatus.IN_STOCK);
            } else if (productStatus.toUpperCase().equals("LOW STOCK") || productStatus.toUpperCase().equals("LOW_STOCK")) {
                product.setProductStatus(ProductStatus.LOW_STOCK);
            }else if (productStatus.toUpperCase().equals("INACTIVE")) {
                product.setProductStatus(ProductStatus.INACTIVE);
            }else{
                throw new Exception("product status not recognized");
            }
            productRepository.save(product);

            WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProduct(product,row.getCell(6).getStringCellValue(),row.getCell(7).getNumericCellValue());
            List<WarehouseProduct> warehouseProductList = new ArrayList<>();
            warehouseProductList.add(warehouseProduct);
            product.setWarehouseProducts(warehouseProductList);
            productRepository.save(product);
        }


    }
}
