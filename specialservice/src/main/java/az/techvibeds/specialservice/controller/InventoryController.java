package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
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
}
