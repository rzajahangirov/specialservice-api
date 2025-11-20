package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.partner.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface PartnerService {
    PartnerReadDto createCustomer(PartnerCreateDto partnerCreateDto, String name);

    PartnerReadDto createSupplier(PartnerCreateDto partnerCreateDto, String name);

    List<PartnerDto> getAllPartnersByCompany(String name);

    void importFromExcel(MultipartFile file, String name) throws Exception;

    PartnerReadDto updatePartner(PartnerUpdateDto partnerUpdateDto, String userEmail) throws Exception;

    void delete(Long id, String userEmail);

    ByteArrayInputStream exportToExcel(String name) throws Exception;

    PartnerTypeDto getPartnerTypes();

    List<PartnerDto> findByPartnerTypeAndCompany_Id(String partnerType, String email) throws Exception;
}
