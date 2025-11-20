package az.techvibeds.specialservice.controller.service;

import az.techvibeds.specialservice.dtos.assignee.*;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.AssigneeService;
import az.techvibeds.specialservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignee")
public class AssigneeController {

    private final AssigneeService assigneeService;
    private final CompanyService companyService;


    @PostMapping
    public ResponseEntity<AssigneeReadDto> create(@RequestBody AssigneeCreateDto dto, Principal principal){
        return ResponseEntity.ok(assigneeService.create(dto, principal));
    }

    @PutMapping
    public ResponseEntity<AssigneeReadDto> update(@RequestBody AssigneeUpdateDto dto, Principal principal){
        return ResponseEntity.ok(assigneeService.update(dto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal){
        assigneeService.delete(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssigneeDetailDto> getAssigneeDetails(@PathVariable Long id, Principal principal) {
        AssigneeDetailDto dto = assigneeService.findAssigneeByIdDetailDto(id, principal.getName());
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<AssigneeServiceDto>> getAllByCompany(Principal principal) {
        Company company = companyService.findByUserEmail(principal.getName());
        return ResponseEntity.ok(assigneeService.getAssigneeByCompany(company));
    }
}

