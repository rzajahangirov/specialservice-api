package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.partner.PartnerCreateDto;
import az.techvibeds.specialservice.dtos.partner.PartnerDto;
import az.techvibeds.specialservice.dtos.partner.PartnerReadDto;
import az.techvibeds.specialservice.dtos.partner.PartnerUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartnerService {
    PartnerCreateDto createCustomer(PartnerCreateDto partnerCreateDto, String name);

    PartnerCreateDto createSupplier(PartnerCreateDto partnerCreateDto, String name);

    List<PartnerDto> getPartners(String name);

    void importFromExcel(MultipartFile file, String name) throws Exception;

    PartnerReadDto updatePartner(PartnerUpdateDto partnerUpdateDto) throws Exception;

    void delete(Long id);
}
