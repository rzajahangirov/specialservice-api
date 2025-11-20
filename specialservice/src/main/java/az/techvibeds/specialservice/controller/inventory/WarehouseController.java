package az.techvibeds.specialservice.controller.inventory;

import az.techvibeds.specialservice.dtos.warehouse.WarehouseCreateDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseReadDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseUpdateDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseReadDto> create(@RequestBody WarehouseCreateDto dto, Principal principal) {
        WarehouseReadDto created = warehouseService.create(dto,principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WarehouseReadDto> update(@RequestBody WarehouseUpdateDto dto, Principal principal) {
        WarehouseReadDto updated = warehouseService.update(dto, principal.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal) {
        warehouseService.delete(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Warehouse deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseReadDto> getById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(warehouseService.getById(id, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseReadDto>> getAllByCompany(Principal principal) {
        return ResponseEntity.ok(warehouseService.getAllByCompanyReadDto(principal.getName()));
    }
}
