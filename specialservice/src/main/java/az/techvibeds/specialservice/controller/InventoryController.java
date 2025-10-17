package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.services.CompanyStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private CompanyStockService companyStockService;

//    @GetMapping
//    public ResponseEntity<InventoryDto> inventory(Principal principal) {
//        CompanyStockInventoryDto stockInventoryDto =  companyStockService.findStockCount(principal.getName());
//    }

}
