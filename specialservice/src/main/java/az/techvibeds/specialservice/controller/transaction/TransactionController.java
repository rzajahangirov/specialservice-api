package az.techvibeds.specialservice.controller.transaction;

import az.techvibeds.specialservice.dtos.partner.*;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
// Satış və Alış İdarəetmə
public class TransactionController {
    private final PartnerService partnerService;

    // Musteri yaratmaq
    @PostMapping("/create-customer")
    public ResponseEntity<PartnerReadDto> createCustomer(@RequestBody PartnerCreateDto partnerCreateDto, Principal principal) {
        PartnerReadDto created = partnerService.createCustomer(partnerCreateDto, principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Techizatci yaratmaq
    @PostMapping("/create-supplier")
    public ResponseEntity<PartnerReadDto> createSupplier(@RequestBody PartnerCreateDto partnerCreateDto, Principal principal) {
        PartnerReadDto created = partnerService.createSupplier(partnerCreateDto, principal.getName());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Musderiler ve Techizatcilar list
    @GetMapping
    public ResponseEntity<List<PartnerDto>> getPartners(Principal principal) {
        List<PartnerDto> partnerDto = partnerService.getAllPartnersByCompany(principal.getName());
        return new ResponseEntity<>(partnerDto, HttpStatus.OK);
    }

    @GetMapping("/partner-types")
    public ResponseEntity<PartnerTypeDto> getPartnerTypes() {
        PartnerTypeDto partnerTypeDtoList = partnerService.getPartnerTypes();
        return ResponseEntity.ok(partnerTypeDtoList);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<PartnerDto>> filterPartners(@RequestParam("filter") String partnerType, Principal principal) throws Exception {
        List<PartnerDto> partnerDtoList = partnerService.findByPartnerTypeAndCompany_Id(partnerType,principal.getName());
        return new ResponseEntity<>(partnerDtoList, HttpStatus.OK);
    }



    // Excel ile idxal
    @PostMapping("/upload-partners")
    public ResponseEntity<ApiResponse> uploadExcel(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        partnerService.importFromExcel(file, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Excel successfully uploaded and saved to DB!", true));
    }

    //export
    @GetMapping("/export-partners")
    public ResponseEntity<byte[]> exportPartnersToExcel(Principal principal) throws Exception {
        ByteArrayInputStream in = partnerService.exportToExcel(principal.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=partners.xlsx");

        return ResponseEntity.ok().headers(headers).body(in.readAllBytes());
    }


    // Update partner elements
    @PutMapping("/update-partner")
    public ResponseEntity<PartnerReadDto> updatePartner(@RequestBody PartnerUpdateDto partnerUpdateDto, Principal principal) throws Exception {
        PartnerReadDto partnerReadDto = partnerService.updatePartner(partnerUpdateDto, principal.getName());
        return new ResponseEntity<>(partnerReadDto, HttpStatus.OK);
    }

    // Delete partner
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePartner(@PathVariable Long id, Principal principal) {
        partnerService.delete(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Partner deleted", true));
    }

}
