package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import az.techvibeds.specialservice.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitGetDto>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }
}

