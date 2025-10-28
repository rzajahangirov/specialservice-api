package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.ProductRepository;
import az.techvibeds.specialservice.repositories.WarehouseProductRepository;
import az.techvibeds.specialservice.repositories.WarehouseRepository;
import az.techvibeds.specialservice.services.ProductService;
import az.techvibeds.specialservice.services.WarehouseProductService;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseProductServiceImpl implements WarehouseProductService {
    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseService warehouseService;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseProduct createWarehouseProduct(Product product, String warehouseName, double quantity) {
        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setProduct(product);
        if (quantity > 0) {
            warehouseProduct.setQuantity((int) quantity);
        }else{
            warehouseProduct.setQuantity(0);
        }
        warehouseProduct.setWarehouse(warehouseService.getWarehouseByName(warehouseName));
        warehouseProductRepository.save(warehouseProduct);
        return warehouseProduct;
    }

    @Override
    public void removeFromWarehouse(String productCode, String warehouseName, Integer quantity, Company company) throws Exception {

        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new Exception("Product not found with code: " + productCode);
        }

        Warehouse warehouse = warehouseService.getWarehouseByNameAndCompanyId(warehouseName,company);
        WarehouseProduct warehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, warehouse);

        if (warehouseProduct == null) {
            throw new Exception("This product does not exist in the specified warehouse.");
        }


        if (warehouseProduct.getQuantity() < quantity) {
            throw new Exception("Not enough stock in warehouse. Current quantity: " + warehouseProduct.getQuantity());
        }


        warehouseProduct.setQuantity(warehouseProduct.getQuantity() - quantity);
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        warehouseProductRepository.save(warehouseProduct);


        if (product.getStock() == 0) {
            product.setProductStatus(ProductStatus.LOW_STOCK);
            productRepository.save(product);
        }
    }

    @Override
    public void transferProductBetweenWarehouses(String productCode, String fromWarehouseName, String toWarehouseName, int quantity) throws Exception {

        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new Exception("Product not found with code: " + productCode);
        }


        Warehouse fromWarehouse = warehouseService.getWarehouseByName(fromWarehouseName);
        WarehouseProduct fromWarehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, fromWarehouse);
        if (fromWarehouseProduct == null) {
            throw new Exception("Product not found in source warehouse: " + fromWarehouseName);
        }


        if (fromWarehouseProduct.getQuantity() < quantity) {
            throw new Exception("Not enough stock in source warehouse. Available: " + fromWarehouseProduct.getQuantity());
        }


        Warehouse toWarehouse = warehouseService.getWarehouseByName(toWarehouseName);
        WarehouseProduct toWarehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, toWarehouse);


        if (toWarehouseProduct == null) {
            toWarehouseProduct = new WarehouseProduct();
            toWarehouseProduct.setProduct(product);
            toWarehouseProduct.setWarehouse(toWarehouse);
            toWarehouseProduct.setQuantity(0);
        }


        fromWarehouseProduct.setQuantity(fromWarehouseProduct.getQuantity() - quantity);
        toWarehouseProduct.setQuantity(toWarehouseProduct.getQuantity() + quantity);


        warehouseProductRepository.save(fromWarehouseProduct);
        warehouseProductRepository.save(toWarehouseProduct);
    }

    @Override
    public Integer updateInventarWarehouseProduct(WarehouseProduct warehouseProduct, String warehouse, Integer stock) {
        WarehouseProduct warehouseProduct1 = warehouseProduct;
        Integer nowQuantity = warehouseProduct.getQuantity();
        warehouseProduct1.setQuantity(stock);
        warehouseProduct1.setWarehouse(warehouseRepository.findByName(warehouse));
        warehouseProductRepository.save(warehouseProduct1);
        return nowQuantity;
    }

    @Override
    public WarehouseProduct findWarehouseProductById(Long id) {
        return warehouseProductRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteWarehouseProduct(Long id) {
        WarehouseProduct warehouseProduct = findWarehouseProductById(id);
        Product product = warehouseProduct.getProduct();
        product.setStock(product.getStock() - warehouseProduct.getQuantity());
        productRepository.save(product);
        warehouseProductRepository.deleteById(id);
    }

}
