package az.techvibeds.specialservice.controller.manufacturing;

import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderCreateDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderReadDto;
import az.techvibeds.specialservice.dtos.productionorder.ProductionOrderUpdateDto;
import az.techvibeds.specialservice.services.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production-order")
public class ProductionOrderController {
    private final ProductionOrderService productionOrderService;

    @PostMapping
    public ResponseEntity<ProductionOrderReadDto> createProductionOrder(@RequestBody ProductionOrderCreateDto createDto, Principal principal) {
        ProductionOrderReadDto productionOrderReadDto = productionOrderService.createProductionServiceAndProduct(createDto, principal.getName());
        return ResponseEntity.ok(productionOrderReadDto);
    }
    @GetMapping
    public ResponseEntity<List<ProductionOrderReadDto>> getAllProductionOrders(Principal principal) {
        List<ProductionOrderReadDto> productionOrderReadDtoList = productionOrderService.getAllByCompanyId(principal.getName());
        return ResponseEntity.ok(productionOrderReadDtoList);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductionOrderReadDto> updateProductionOrder(@PathVariable Long id, @RequestBody ProductionOrderUpdateDto updateDto, Principal principal) {
        ProductionOrderReadDto productionOrderReadDto = productionOrderService.updateProductionOrderAndProduct(id, updateDto, principal.getName());
        return ResponseEntity.ok(productionOrderReadDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<Void>> deleteProductionOrder(@PathVariable Long id, Principal principal) {
        productionOrderService.deleteById(id ,principal);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filer")
    public ResponseEntity<List<ProductionOrderReadDto>> getAllProductionOrdersFiler(@RequestParam(required = false) String status,
                                                                                    Principal principal) {
       List<ProductionOrderReadDto> productionOrderReadDtoList = productionOrderService.getFilteredProductionOrdersAndProducts(status, principal.getName());
       return ResponseEntity.ok(productionOrderReadDtoList);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductionOrderReadDto>> getAllProductionOrdersSearched(@RequestParam(required = false) String keyword, Principal principal) {
        List<ProductionOrderReadDto> productionOrderReadDtoList = productionOrderService.getSearchedProductionOrdersAndProducts(keyword, principal);
        return ResponseEntity.ok(productionOrderReadDtoList);
    }

}
