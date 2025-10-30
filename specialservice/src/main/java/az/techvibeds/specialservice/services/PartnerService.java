package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.partner.PartnerCreateDto;
import az.techvibeds.specialservice.dtos.partner.PartnerDto;
import az.techvibeds.specialservice.dtos.partner.PartnerReadDto;
import az.techvibeds.specialservice.dtos.partner.PartnerUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface PartnerService {
    PartnerReadDto createCustomer(PartnerCreateDto partnerCreateDto, String name);

    PartnerReadDto createSupplier(PartnerCreateDto partnerCreateDto, String name);

    List<PartnerDto> getPartners(String name);

    void importFromExcel(MultipartFile file, String name) throws Exception;

    PartnerReadDto updatePartner(PartnerUpdateDto partnerUpdateDto) throws Exception;

    void delete(Long id);

    ByteArrayInputStream exportToExcel(String name) throws Exception;
}
