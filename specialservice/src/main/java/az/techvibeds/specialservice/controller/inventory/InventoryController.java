package az.techvibeds.specialservice.controller.inventory;


import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.inventory.ProductStatusDto;
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
    private final ProductService productService;
    private final CompanyService companyService;
    private final WarehouseProductService warehouseProductService;
    private final InventoryService inventoryService;

    //Melumatlarin getirilmesi
    @GetMapping
    public ResponseEntity<InventoryDto> inventory(Principal principal) {
        InventoryDto inventoryDto = inventoryService.getInventory(principal.getName());
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
    public ResponseEntity<ApiResponse> removeFromWarehouse(@RequestParam String productCode, @RequestParam Long warehouseId, @RequestParam Integer quantity, Principal principal) {
        try {
            warehouseProductService.removeFromWarehouse(productCode, warehouseId, quantity, principal.getName());
            return ResponseEntity.ok(new ApiResponse("Product removed from warehouse successfully.", true));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error during removing: " + e.getMessage(), false));
        }
    }

    //transfer
    @PostMapping("/warehouse-transfer")
    public ResponseEntity<ApiResponse> transferProduct(@RequestParam String productCode, @RequestParam Long fromWarehouseId, @RequestParam Long toWarehouseId, @RequestParam int quantity, Principal principal) {
        try {
            warehouseProductService.transferProductBetweenWarehouses(productCode, fromWarehouseId, toWarehouseId, quantity, principal.getName());
            return ResponseEntity.ok(new ApiResponse("Product successfully transferred from " + fromWarehouseId + " to " + toWarehouseId, true));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error during transfer: " + e.getMessage(), false));
        }
    }

    //update inventory(warehouseProduct vasiesi ile hem product hemde warehouseProductUpdate olunur)
    @PutMapping
    public ResponseEntity<ProductReadDto> updateInventory(@RequestBody ProductInventoryUpdateDto productInventoryUpdateDto, Principal principal) throws Exception {
        ProductReadDto dto = productService.updateInventorProduct(productInventoryUpdateDto, principal.getName());
        return ResponseEntity.ok(dto);
    }


    //delete inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id, Principal principal) {
        warehouseProductService.deleteWarehouseProduct(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully.", true));
    }

    @GetMapping("/get-status")
    public ResponseEntity<ProductStatusDto> getInventoryStatus() {
        ProductStatusDto productStatusDto = inventoryService.getProductStatuses();
        return ResponseEntity.ok(productStatusDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductInventoryDto>> filterProducts( @RequestParam(required = false) Long warehouseId,
                                                                     @RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) String productStatus,
                                                                     Principal principal){
        List<ProductInventoryDto> productInventoryDtoList = productService
                .getFilteredProducts(warehouseId, categoryId, productStatus, principal.getName());
        return ResponseEntity.ok(productInventoryDtoList);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductInventoryDto>> searchProducts(@RequestParam String productName, Principal principal) {
        List<ProductInventoryDto> productInventoryDtoList = productService.getSearchedProducts(productName, principal);
        return ResponseEntity.ok(productInventoryDtoList);
    }

}
