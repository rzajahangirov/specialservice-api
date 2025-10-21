package az.techvibeds.specialservice.controller;


import az.techvibeds.specialservice.dtos.company.CompanyCreateDto;
import az.techvibeds.specialservice.dtos.company.CompanyReadDto;
import az.techvibeds.specialservice.dtos.company.CompanyUpdateDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyReadDto> create(@RequestBody CompanyCreateDto dto){
        CompanyReadDto created = companyService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CompanyReadDto> update(@RequestBody CompanyUpdateDto dto){
        CompanyReadDto updated = companyService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        companyService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Company deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyReadDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyReadDto>> getAll(){
        return ResponseEntity.ok(companyService.getAll());
    }
}
