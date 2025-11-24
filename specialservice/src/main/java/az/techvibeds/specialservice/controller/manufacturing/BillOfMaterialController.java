package az.techvibeds.specialservice.controller.manufacturing;

import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialCreateDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialReadDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialUpdateDto;
import az.techvibeds.specialservice.services.BillOfMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bom")
public class BillOfMaterialController {
    private final BillOfMaterialService billOfMaterialService;

    @GetMapping
    public ResponseEntity<List<BillOfMaterialReadDto>> getAllBillOfMaterials(Principal principal) {
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = billOfMaterialService.getAllByCompany(principal.getName());
        return ResponseEntity.ok(billOfMaterialReadDtoList);
    }
    @PostMapping
    public ResponseEntity<BillOfMaterialReadDto> createBillOfMaterial(@RequestBody BillOfMaterialCreateDto createDto, Principal principal) {
        BillOfMaterialReadDto billOfMaterialReadDto = billOfMaterialService.createBom(createDto, principal.getName());
        return ResponseEntity.ok(billOfMaterialReadDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillOfMaterialReadDto> updateBillOfMaterial(@PathVariable Long id, @RequestBody BillOfMaterialUpdateDto updateDto, Principal principal) {
        BillOfMaterialReadDto billOfMaterialReadDto = billOfMaterialService.updateBom(id, updateDto, principal.getName());
        return ResponseEntity.ok(billOfMaterialReadDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillOfMaterial(@PathVariable Long id, Principal principal) {
        billOfMaterialService.deleteById(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filer")
    public ResponseEntity<List<BillOfMaterialReadDto>> getAllBillOfMaterialsFiler(@RequestParam(required = false) String status,
                                                                                  Principal principal) {
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = billOfMaterialService.getAllFilteredByStatus(status, principal);
        return ResponseEntity.ok(billOfMaterialReadDtoList);
    }
    @GetMapping("/search")
    public ResponseEntity<List<BillOfMaterialReadDto>> getAllBillOfMaterialsSearched(@RequestParam(required = false) String keyword, Principal principal) {
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = billOfMaterialService.getAllSearchedBom(keyword, principal);
        return ResponseEntity.ok(billOfMaterialReadDtoList);
    }

}
