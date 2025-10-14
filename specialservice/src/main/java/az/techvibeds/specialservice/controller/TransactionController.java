package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.partner.PartnerCreateDto;
import az.techvibeds.specialservice.dtos.partner.PartnerDto;
import az.techvibeds.specialservice.dtos.partner.PartnerReadDto;
import az.techvibeds.specialservice.dtos.partner.PartnerUpdateDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
//Satış və Alış İdarəetmə
public class TransactionController {
    private final PartnerService partnerService;

    //Musteri yaratmaq
    @PostMapping("/create-customer")
    public ResponseEntity<PartnerCreateDto> createCustomer(@RequestBody PartnerCreateDto partnerCreateDto, Principal principal) {
        PartnerCreateDto created = partnerService.createCustomer(partnerCreateDto, principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Techizatci yaratamaq
    @PostMapping("/create-supplier")
    public ResponseEntity<PartnerCreateDto> createSupplier(@RequestBody PartnerCreateDto partnerCreateDto, Principal principal) {
        PartnerCreateDto created = partnerService.createSupplier(partnerCreateDto, principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Musderiler ve Techizatcilar list
    @GetMapping("/partners")
    public ResponseEntity<List<PartnerDto>> getPartners(Principal principal) {
        List<PartnerDto> partnerDto = partnerService.getPartners(principal.getName());
        return new ResponseEntity<>(partnerDto, HttpStatus.OK);
    }

    //Excel ile idxal
    @PostMapping("/upload-partners")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file, Principal principal) {
        try {
            partnerService.importFromExcel(file, principal.getName());
            return ResponseEntity.ok("Excel successfully uploaded and saved to DB!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    //Update partner elements
    @PutMapping("update-partner")
    public ResponseEntity<PartnerReadDto> updatePartner(@RequestBody PartnerUpdateDto partnerUpdateDto) {
        try {
            PartnerReadDto partnerReadDto = partnerService.updatePartner(partnerUpdateDto);
            return new ResponseEntity<>(partnerReadDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Delete partner
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePartner(@PathVariable Long id) {
        partnerService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Partner deleted", true));

    }

}
