package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.ProductRepository;
import az.techvibeds.specialservice.repositories.WarehouseProductRepository;
import az.techvibeds.specialservice.repositories.WarehouseRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.WarehouseProductService;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseProductServiceImpl implements WarehouseProductService {
    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseService warehouseService;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final CompanyService companyService;

    @Override
    public WarehouseProduct createWarehouseProduct(Product product, Long warehouseId, double quantity) {
        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setProduct(product);
        if (quantity > 0) {
            warehouseProduct.setQuantity((int) quantity);
        }else{
            warehouseProduct.setQuantity(0);
        }
        warehouseProduct.setWarehouse(warehouseService.getWarehouseById(warehouseId));
        warehouseProductRepository.save(warehouseProduct);
        return warehouseProduct;
    }

    @Override
    public void removeFromWarehouse(String productCode, Long warehouseId, Integer quantity, String userEmail) throws Exception {

        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new Exception("Product not found with code: " + productCode);
        }

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        WarehouseProduct warehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, warehouse);

        if (warehouseProduct == null) {
            throw new Exception("This product does not exist in the specified warehouse.");
        }
        Company company = companyService.findByUserEmail(userEmail);
        if (warehouse.getCompany() == company && product.getCompany() == company) {

            if (warehouseProduct.getQuantity() < quantity) {
                throw new Exception("Not enough stock in warehouse. Current quantity: " + warehouseProduct.getQuantity());
            }


            if (quantity < 0) {
                warehouseProduct.setQuantity(warehouseProduct.getQuantity() - quantity);
            } else {
                throw new Exception("Quantity mast be greater than zero");
            }
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            warehouseProductRepository.save(warehouseProduct);


            if (product.getStock() == 0) {
                product.setProductStatus(ProductStatus.LOW_STOCK);
                productRepository.save(product);
            }
        }else {
            throw new Exception("Mehsul ve ya anbar sizin sirkete aid deyil");
        }
    }

    @Override
    public void transferProductBetweenWarehouses(String productCode, Long fromWarehouseId, Long toWarehouseId, int quantity, String userEmail) throws Exception {

        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new Exception("Product not found with code: " + productCode);
        }


        Warehouse fromWarehouse = warehouseService.getWarehouseById(fromWarehouseId);
        WarehouseProduct fromWarehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, fromWarehouse);
        if (fromWarehouseProduct == null) {
            throw new Exception("Product not found in source warehouse: " + fromWarehouseId);
        }


        if (fromWarehouseProduct.getQuantity() < quantity) {
            throw new Exception("Not enough stock in source warehouse. Available: " + fromWarehouseProduct.getQuantity());
        }


        Warehouse toWarehouse = warehouseService.getWarehouseById(toWarehouseId);
        WarehouseProduct toWarehouseProduct = warehouseProductRepository.findByProductAndWarehouse(product, toWarehouse);
        Company company = companyService.findByUserEmail(userEmail);
        if (fromWarehouse.getCompany() == company && product.getCompany() == company && toWarehouse.getCompany() == company) {


            if (toWarehouseProduct == null) {
                toWarehouseProduct = new WarehouseProduct();
                toWarehouseProduct.setProduct(product);
                toWarehouseProduct.setWarehouse(toWarehouse);
                toWarehouseProduct.setQuantity(0);
            }

            if (toWarehouseProduct.getQuantity() > 0) {
                fromWarehouseProduct.setQuantity(fromWarehouseProduct.getQuantity() - quantity);
                toWarehouseProduct.setQuantity(toWarehouseProduct.getQuantity() + quantity);
            } else {
                throw new Exception("Quantity mast be greater than zero");
            }

            warehouseProductRepository.save(fromWarehouseProduct);
            warehouseProductRepository.save(toWarehouseProduct);
        }else {
            throw new Exception("Bu mehsula ve ya anbarlara giris icazeniz yoxdur, sizin sirkete aid deyiller");
        }
    }

    @Override
    public Integer updateInventoryWarehouseProduct(WarehouseProduct warehouseProduct, Long warehouseId, Integer quantity) {
        WarehouseProduct warehouseProduct1 = warehouseProduct;
        Integer nowQuantity = warehouseProduct.getQuantity();
        if (quantity>0) warehouseProduct1.setQuantity(quantity);
        warehouseProduct1.setWarehouse(warehouseRepository.findById(warehouseId).orElseThrow());
        warehouseProductRepository.save(warehouseProduct1);
        return nowQuantity;
    }

    @Override
    public WarehouseProduct findWarehouseProductById(Long id) {
        return warehouseProductRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteWarehouseProduct(Long id, String userEmail) {
        WarehouseProduct warehouseProduct = findWarehouseProductById(id);
        Product product = warehouseProduct.getProduct();
        if (product.getCompany() == companyService.findByUserEmail(userEmail)) {
            product.setStock(product.getStock() - warehouseProduct.getQuantity());
            productRepository.save(product);
            warehouseProductRepository.deleteById(id);
        }else {
            throw new RuntimeException("Bu mehsul sirketinize aid deyil");
        }
    }

    @Override
    public WarehouseProduct createWarehouseProductFromExcel(Product product, String warehouseName, double quantity) {
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
    public List<WarehouseProduct> findWarehouseProductByCompany_Id(List<Warehouse> warehouseList) {
        List<WarehouseProduct> warehouseProductList = new ArrayList<>();
        for (Warehouse warehouse : warehouseList) {
            List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findAllByWarehouse_Id(warehouse.getId());
            for (WarehouseProduct warehouseProduct : warehouseProducts) {
                warehouseProductList.add(warehouseProduct);
            }
        }
        return warehouseProductList;
    }

    @Override
    public List<WarehouseProduct> findWarehouseProductByWarehouseId(Long warehouseId) {
        return warehouseProductRepository.findAllByWarehouse_Id(warehouseId);
    }

}
