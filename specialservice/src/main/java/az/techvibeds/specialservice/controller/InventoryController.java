package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductCreateDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryUpdateDto;
import az.techvibeds.specialservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final CompanyStockService companyStockService;
    private final ProductService productService;
    private final WarehouseActivityService warehouseActivityService;
    private final CompanyService companyService;
    private final WarehouseProductService warehouseProductService;

    //Melumatlarin getirilmesi
    @GetMapping
    public ResponseEntity<InventoryDto> inventory(Principal principal) {
        Long companyId = companyService.findByUserEmail(principal.getName()).getId();

        //Cari Stock Deyeri
        CompanyStockInventoryDto stockInventoryDto =  companyStockService.findStockCount(companyId);
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setStockCount(stockInventoryDto.getStockCount());
        inventoryDto.setGrowthRate(stockInventoryDto.getGrowthRate()+"%");

        //Umumi mehsul sayisi
        Integer productCount = productService.getProductCountByCompany(companyId);
        inventoryDto.setProductCount(productCount);

        //Son Transfer
        String lastTransfer = warehouseActivityService.getLastTransfer(companyId);
        inventoryDto.setLastTransfer(lastTransfer);

        //Inventar Siyahisi
        List<ProductInventoryDto> productInventoryDtoList = productService.getProductsByCompanyId(companyId);
        inventoryDto.setProductInventoryDtoList(productInventoryDtoList);

        return ResponseEntity.ok(inventoryDto);
    }


    //Excelden import
    @PostMapping("/upload-products")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        productService.uploadProductsFromExcel(file,companyService.findByUserEmail(principal.getName()));
        return ResponseEntity.ok("Excel successfully uploaded and saved to DB!");
    }

    //Anbara qeydiyyat
    @PostMapping("/record")
    public ResponseEntity<ProductCreateDto> inventoryRecord(@RequestBody ProductCreateDto productCreateDto, Principal principal) throws Exception {
        ProductCreateDto created = productService.createProductAndInventoryRecord(productCreateDto,companyService.findByUserEmail(principal.getName()));
        return ResponseEntity.ok(created);
    }
    //Anbardan cixis
    @PostMapping("/remove")
    public ResponseEntity<String> removeFromWarehouse(@RequestParam String productCode, @RequestParam String warehouseName, @RequestParam Integer quantity, Principal principal) throws Exception {
        try {
            warehouseProductService.removeFromWarehouse(productCode, warehouseName, quantity,companyService.findByUserEmail(principal.getName()));
            return ResponseEntity.ok("Product removed from warehouse successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //transfer
    @PostMapping("/warehouse-transfer")
    public ResponseEntity<String> transferProduct(@RequestParam String productCode, @RequestParam String fromWarehouse, @RequestParam String toWarehouse, @RequestParam int quantity) {
        try {
            warehouseProductService.transferProductBetweenWarehouses(productCode, fromWarehouse, toWarehouse, quantity);
            return ResponseEntity.ok("Product successfully transferred from " + fromWarehouse + " to " + toWarehouse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //update inventar
    @PutMapping
    public ResponseEntity<String> updateInventory(@RequestBody ProductInventoryUpdateDto productInventoryUpdateDto) throws Exception {
        productService.updateInventorProduct(productInventoryUpdateDto);
        return ResponseEntity.ok("Product updated successfully.");
    }


    //delete inventar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) throws Exception {
        warehouseProductService.deleteWarehouseProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

}
