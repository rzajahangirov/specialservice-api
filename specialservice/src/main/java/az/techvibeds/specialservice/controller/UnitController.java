package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.unit.UnitCreateDto;
import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import az.techvibeds.specialservice.dtos.unit.UnitReadDto;
import az.techvibeds.specialservice.dtos.unit.UnitUpdateDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit")
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitGetDto>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }
    @PostMapping
    public ResponseEntity<UnitReadDto> create(@RequestBody UnitCreateDto dto){
        UnitReadDto created = unitService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UnitReadDto> update(@RequestBody UnitUpdateDto dto){
        UnitReadDto updated = unitService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        unitService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Unit deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitReadDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.getById(id));
    }
}

