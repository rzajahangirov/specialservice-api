package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductCreateDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryUpdateDto;
import az.techvibeds.specialservice.dtos.product.ProductReadDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
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
    public ResponseEntity<ApiResponse> uploadExcel(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        productService.uploadProductsFromExcel(file,companyService.findByUserEmail(principal.getName()));
        return ResponseEntity.ok(new ApiResponse("Excel successfully uploaded and saved to DB!" ,true));
    }

    //Anbara qeydiyyat
    @PostMapping("/record")
    public ResponseEntity<ProductReadDto> inventoryRecord(@RequestBody ProductCreateDto productCreateDto, Principal principal) throws Exception {
        ProductReadDto created = productService.createProductAndInventoryRecord(productCreateDto,companyService.findByUserEmail(principal.getName()));
        return ResponseEntity.ok(created);
    }
    //Anbardan cixis
    @PostMapping("/remove")
    public ResponseEntity<ApiResponse> removeFromWarehouse(@RequestParam String productCode, @RequestParam Long warehouseId, @RequestParam Integer quantity) {
        try {
            warehouseProductService.removeFromWarehouse(productCode, warehouseId, quantity);
            return ResponseEntity.ok(new ApiResponse("Product removed from warehouse successfully.", true));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error during removing: " + e.getMessage(), false));
        }
    }

    //transfer
    @PostMapping("/warehouse-transfer")
    public ResponseEntity<ApiResponse> transferProduct(@RequestParam String productCode, @RequestParam Long fromWarehouseId, @RequestParam Long toWarehouseId, @RequestParam int quantity) {
        try {
            warehouseProductService.transferProductBetweenWarehouses(productCode, fromWarehouseId, toWarehouseId, quantity);
            return ResponseEntity.ok(new ApiResponse("Product successfully transferred from " + fromWarehouseId + " to " + toWarehouseId, true));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error during transfer: " + e.getMessage(), false));
        }
    }

    //update inventory(warehouseProduct vasiesi ile hem product hemde warehouseProductUpdate olunur)
    @PutMapping
    public ResponseEntity<ProductReadDto> updateInventory(@RequestBody ProductInventoryUpdateDto productInventoryUpdateDto) throws Exception {
        ProductReadDto dto = productService.updateInventorProduct(productInventoryUpdateDto);
        return ResponseEntity.ok(dto);
    }


    //delete inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) {
        warehouseProductService.deleteWarehouseProduct(id);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully.", true));
    }

}
