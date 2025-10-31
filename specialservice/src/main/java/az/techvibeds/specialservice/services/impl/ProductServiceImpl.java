package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.product.ProductCreateDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryUpdateDto;
import az.techvibeds.specialservice.dtos.product.ProductReadDto;
import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.ProductRepository;
import az.techvibeds.specialservice.services.*;
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
            productInventoryDto.setId(warehouseProduct.getId());
            productInventoryDto.setProductCode(product.getProductCode());
            productInventoryDto.setName(product.getName());
            productInventoryDto.setPrice(product.getPrice());
            //anbarda ne qeder varsa o sayi gosderilir
            productInventoryDto.setStock(warehouseProduct.getQuantity());
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
            if(!productRepository.existsByProductCode(row.getCell(1).getStringCellValue())) {
                Product product = new Product();
                product.setCompany(company);
                product.setName(row.getCell(0).getStringCellValue());
                product.setProductCode(row.getCell(1).getStringCellValue());

                //stock sayi anbarlardaki sayilarin cemi seklinde yazilir
                if (row.getCell(6).getNumericCellValue()>0) {
                    double stockDouble = row.getCell(6).getNumericCellValue();
                    Long stock = (long) stockDouble;
                    product.setStock(stock);
                }else{
                    product.setStock(0L);
                }

                BigDecimal price = BigDecimal.valueOf(row.getCell(2).getNumericCellValue());
                product.setPrice(price);

                product.setCategory(categoryService.findByName(row.getCell(3).getStringCellValue()));

                String productStatus = row.getCell(4).getStringCellValue();
                if (productStatus.toUpperCase().equals("IN STOCK") || productStatus.toUpperCase().equals("IN_STOCK")) {
                    product.setProductStatus(ProductStatus.IN_STOCK);
                } else if (productStatus.toUpperCase().equals("LOW STOCK") || productStatus.toUpperCase().equals("LOW_STOCK")) {
                    product.setProductStatus(ProductStatus.LOW_STOCK);
                } else if (productStatus.toUpperCase().equals("INACTIVE")) {
                    product.setProductStatus(ProductStatus.INACTIVE);
                } else {
                    throw new Exception("product status not recognized");
                }
                productRepository.save(product);

                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductFromExcel(product, row.getCell(5).getStringCellValue(), row.getCell(6).getNumericCellValue());
                List<WarehouseProduct> warehouseProductList = new ArrayList<>();
                warehouseProductList.add(warehouseProduct);
                product.setWarehouseProducts(warehouseProductList);
                productRepository.save(product);
            }else{
                Product product = productRepository.findByProductCode(row.getCell(1).getStringCellValue());
                WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProductFromExcel(product, row.getCell(5).getStringCellValue(), row.getCell(6).getNumericCellValue());
                List<WarehouseProduct> warehouseProductList = product.getWarehouseProducts();
                warehouseProductList.add(warehouseProduct);
                product.setWarehouseProducts(warehouseProductList);

                //stock sayi anbarlardaki sayilarin cemi seklinde yazilir
                if (row.getCell(6).getNumericCellValue()>0) {
                    double stockDouble = row.getCell(6).getNumericCellValue();
                    Long stock = (long) stockDouble;
                    product.setStock(product.getStock()+stock);
                }else {
                    product.setStock(0L);
                }


                productRepository.save(product);
            }

        }


    }

    @Override
    public ProductReadDto createProductAndInventoryRecord(ProductCreateDto productCreateDto, Company byUserEmail) throws Exception {
        if(!productRepository.existsByProductCode(productCreateDto.getProductCode())){
            Product product = new Product();
            product.setCompany(byUserEmail);
            product.setName(productCreateDto.getName());
            product.setProductCode(productCreateDto.getProductCode());
            product.setPrice(productCreateDto.getPrice());
            product.setCategory(categoryService.findById(productCreateDto.getCategoryId()));

            if (productCreateDto.getProductStatus().toUpperCase().equals("IN STOCK") || productCreateDto.getProductStatus().toUpperCase().equals("IN_STOCK")) {
                product.setProductStatus(ProductStatus.IN_STOCK);
            } else if (productCreateDto.getProductStatus().toUpperCase().equals("LOW STOCK") || productCreateDto.getProductStatus().toUpperCase().equals("LOW_STOCK")) {
                product.setProductStatus(ProductStatus.LOW_STOCK);
            } else if (productCreateDto.getProductStatus().toUpperCase().equals("INACTIVE")) {
                product.setProductStatus(ProductStatus.INACTIVE);
            } else {
                throw new Exception("product status not recognized");
            }
            if (productCreateDto.getQuantity()>0){product.setStock(productCreateDto.getQuantity().longValue());}
            else{product.setStock(0L);}
            productRepository.save(product);

            WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProduct(product, productCreateDto.getWarehouseId(), productCreateDto.getQuantity());
            List<WarehouseProduct> warehouseProductList = new ArrayList<>();
            warehouseProductList.add(warehouseProduct);
            product.setWarehouseProducts(warehouseProductList);
            productRepository.save(product);
            return mapToProductReadDto(product,warehouseProduct);
        }else{
            Product product = productRepository.findByProductCode(productCreateDto.getProductCode());
            WarehouseProduct warehouseProduct = warehouseProductService.createWarehouseProduct(product, productCreateDto.getWarehouseId(), productCreateDto.getQuantity());
            List<WarehouseProduct> warehouseProductList = new ArrayList<>();
            warehouseProductList.add(warehouseProduct);
            product.setWarehouseProducts(warehouseProductList);
            if (productCreateDto.getQuantity()>0){product.setStock(product.getStock()+productCreateDto.getQuantity().longValue());}
            productRepository.save(product);
            return mapToProductReadDto(product,warehouseProduct);
        }

    }

    @Override
    public ProductReadDto updateInventorProduct(ProductInventoryUpdateDto productInventoryUpdateDto) throws Exception {
        WarehouseProduct warehouseProduct = warehouseProductService.findWarehouseProductById(productInventoryUpdateDto.getWarehouseProductId());
        Product product = warehouseProduct.getProduct();
        product.setName(productInventoryUpdateDto.getName());
        product.setProductCode(productInventoryUpdateDto.getProductCode());
        product.setPrice(productInventoryUpdateDto.getPrice());
        product.setCategory(categoryService.findById(productInventoryUpdateDto.getCategoryId()));

        if (productInventoryUpdateDto.getProductStatus().toUpperCase().equals("IN STOCK") || productInventoryUpdateDto.getProductStatus().toUpperCase().equals("IN_STOCK")) {
            product.setProductStatus(ProductStatus.IN_STOCK);
        }else if (productInventoryUpdateDto.getProductStatus().toUpperCase().equals("LOW_STOCK") || productInventoryUpdateDto.getProductStatus().toUpperCase().equals("LOW_STOCK")) {
            product.setProductStatus(ProductStatus.LOW_STOCK);
        }else if (productInventoryUpdateDto.getProductStatus().toUpperCase().equals("INACTIVE")) {
            product.setProductStatus(ProductStatus.INACTIVE);
        }else {
            throw new Exception(" product status not recognized");
        }

        Integer stock = warehouseProductService.updateInventoryWarehouseProduct(warehouseProduct,productInventoryUpdateDto.getWarehouseId(),productInventoryUpdateDto.getQuantity());

        if (productInventoryUpdateDto.getQuantity()>0){product.setStock(product.getStock()-stock+productInventoryUpdateDto.getQuantity().longValue());}
        else{throw new Exception("Quantity must be greater than zero");}

        productRepository.save(product);
        return mapToProductReadDto(product,warehouseProduct);
    }

    private ProductReadDto mapToProductReadDto(Product product, WarehouseProduct warehouseProduct) {
        ProductReadDto productReadDto = modelMapper.map(product, ProductReadDto.class);
        productReadDto.setWarehouseProductId(warehouseProduct.getId());
        productReadDto.setTotalStock(product.getStock());
        productReadDto.setCategoryName(product.getCategory().getName());
        productReadDto.setProductStatus(product.getProductStatus().name());
        productReadDto.setWarehouseName(warehouseProduct.getWarehouse().getName());
        productReadDto.setQuantity(warehouseProduct.getQuantity());
        return productReadDto;
    }



}
