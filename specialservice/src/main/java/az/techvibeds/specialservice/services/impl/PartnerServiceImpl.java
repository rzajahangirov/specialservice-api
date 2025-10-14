package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.balace.CreateBalanceDto;
import az.techvibeds.specialservice.dtos.partner.PartnerCreateDto;
import az.techvibeds.specialservice.dtos.partner.PartnerDto;
import az.techvibeds.specialservice.dtos.partner.PartnerReadDto;
import az.techvibeds.specialservice.dtos.partner.PartnerUpdateDto;
import az.techvibeds.specialservice.enums.PartnerType;
import az.techvibeds.specialservice.models.Balance;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Partner;
import az.techvibeds.specialservice.repositories.PartnerRepository;
import az.techvibeds.specialservice.services.BalanceService;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.PartnerService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final BalanceService balanceService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Override
    public PartnerCreateDto createCustomer(PartnerCreateDto partnerCreateDto, String name) {
        try{
            Partner partner = new Partner();
            partner.setName(partnerCreateDto.getName());
            partner.setContactPerson(partnerCreateDto.getContactPerson());
            partner.setEmail(partnerCreateDto.getEmail());
            partner.setPhone(partnerCreateDto.getPhone());

            CreateBalanceDto balanceDto = new CreateBalanceDto();
            balanceDto.setAmount(partnerCreateDto.getBalance());
            balanceDto.setCurrencyType(partnerCreateDto.getCurrency());
            balanceDto.setPartner(partner);
            Balance balance = balanceService.createBalace(balanceDto);
            partner.setBalance(balance);

            partner.setPartnerType(PartnerType.CUSTOMER);

            Company company = companyService.findByUserEmail(name);
            partner.setCompany(company);
            partnerRepository.save(partner);
            return partnerCreateDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public PartnerCreateDto createSupplier(PartnerCreateDto partnerCreateDto, String name) {
        try{
            Partner partner = new Partner();
            partner.setName(partnerCreateDto.getName());
            partner.setContactPerson(partnerCreateDto.getContactPerson());
            partner.setEmail(partnerCreateDto.getEmail());
            partner.setPhone(partnerCreateDto.getPhone());

            CreateBalanceDto balanceDto = new CreateBalanceDto();
            balanceDto.setAmount(partnerCreateDto.getBalance());
            balanceDto.setCurrencyType(partnerCreateDto.getCurrency());
            balanceDto.setPartner(partner);
            Balance balance = balanceService.createBalace(balanceDto);
            partner.setBalance(balance);

            partner.setPartnerType(PartnerType.SUPPLIER);

            Company company = companyService.findByUserEmail(name);
            partner.setCompany(company);

            partnerRepository.save(partner);
            return partnerCreateDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PartnerDto> getPartners(String name) {
        Company company = companyService.findByUserEmail(name);
        List<Partner> partnerList = partnerRepository.findAllByCompanyId(company.getId());
        List<PartnerDto> partnerDtoList = new ArrayList<>();
        for (Partner partner : partnerList) {
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setName(partner.getName());
            partnerDto.setContactPerson(partner.getContactPerson());
            partnerDto.setEmail(partner.getEmail());
            partnerDto.setPhone(partner.getPhone());
            partnerDto.setCurrency(partner.getBalance().getCurrencyType());
            partnerDto.setBalance(partner.getBalance().getAmount());
            String partnerType = partner.getPartnerType().toString();
            partnerType = partnerType.substring(0, 1).toUpperCase() + partnerType.substring(1).toLowerCase();
            partnerDto.setPartnerType(partnerType);
            partnerDtoList.add(partnerDto);
        }

        return partnerDtoList;
    }

    @Override
    public void importFromExcel(MultipartFile file, String name) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, String> currencyMap = Map.of(
                "₼", "manat",
                "$", "USD",
                "€", "EUR"
        );
        Company company = companyService.findByUserEmail(name);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Partner partner = new Partner();
            partner.setName(row.getCell(0).getStringCellValue());
            partner.setContactPerson(row.getCell(1).getStringCellValue());
            partner.setEmail(row.getCell(2).getStringCellValue());
            partner.setPhone(row.getCell(3).getStringCellValue());

            CreateBalanceDto balanceDto = new CreateBalanceDto();
            balanceDto.setPartner(partner);
            if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                CellStyle style = row.getCell(4).getCellStyle();
                String formatString = style.getDataFormatString();
                String currencyType = formatString.replaceAll("[#,0.]+", "").trim();
                currencyType = currencyMap.getOrDefault(currencyType, "UNKNOWN");
                balanceDto.setCurrencyType(currencyType);
                balanceDto.setAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
            }else{
                throw new Exception("The Excel columns are not correct.");
            }
            balanceService.createBalace(balanceDto);

            if (row.getCell(5).toString().toUpperCase().equals("CUSTOMER")) {
                partner.setPartnerType(PartnerType.CUSTOMER);
            }else if (row.getCell(5).toString().toUpperCase().equals("SUPPLIER")) {
                partner.setPartnerType(PartnerType.SUPPLIER);
            }else{
                throw new Exception("Unknown partner");
            }
            partner.setCompany(company);
            partnerRepository.save(partner);
        }


    }

    @Override
    public PartnerReadDto updatePartner(PartnerUpdateDto partnerUpdateDto) throws Exception {
        Partner partner = partnerRepository.findById(partnerUpdateDto.getId()).orElseThrow(() -> new RuntimeException("Partner not found"));
        if(partnerUpdateDto.getName() != null) partner.setName(partnerUpdateDto.getName());
        if(partnerUpdateDto.getContactPerson() != null) partner.setContactPerson(partnerUpdateDto.getContactPerson());
        if(partnerUpdateDto.getEmail() != null) partner.setEmail(partnerUpdateDto.getEmail());
        if(partnerUpdateDto.getPhone() != null) partner.setPhone(partnerUpdateDto.getPhone());

        Balance balance = new Balance();
        if(partnerUpdateDto.getBalance() != null) balance.setAmount(partnerUpdateDto.getBalance());
        if(partnerUpdateDto.getCurrency() != null) balance.setCurrencyType(partnerUpdateDto.getCurrency());
        partner.setBalance(balance);
        if (partnerUpdateDto.getCustomerType() != null) {
            if (partnerUpdateDto.getCustomerType().toUpperCase().equals("CUSTOMER")) {
                partner.setPartnerType(PartnerType.CUSTOMER);
            } else if (partnerUpdateDto.getCustomerType().toUpperCase().equals("SUPPLIER")) {
                partner.setPartnerType(PartnerType.SUPPLIER);
            } else {
                throw new Exception("Unknown partner");
            }
        }
        partnerRepository.save(partner);

        return mapToReadDto(partnerUpdateDto);
    }

    @Override
    public void delete(Long id) {
        Partner partner = partnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Partner not found"));
    partnerRepository.delete(partner);
    }

    private PartnerReadDto mapToReadDto(PartnerUpdateDto partnerUpdateDto) {
        PartnerReadDto partnerReadDto = new PartnerReadDto();
        partnerReadDto.setId(partnerUpdateDto.getId());
        if(partnerUpdateDto.getName() != null) partnerReadDto.setName(partnerUpdateDto.getName());
        if(partnerUpdateDto.getContactPerson() != null) partnerReadDto.setContactPerson(partnerUpdateDto.getContactPerson());
        if(partnerUpdateDto.getEmail() != null) partnerReadDto.setEmail(partnerUpdateDto.getEmail());
        if(partnerUpdateDto.getPhone() != null) partnerReadDto.setPhone(partnerUpdateDto.getPhone());
        if (partnerUpdateDto.getBalance() != null) partnerReadDto.setBalance(partnerUpdateDto.getBalance());
        if (partnerUpdateDto.getCurrency() != null) partnerReadDto.setCurrency(partnerUpdateDto.getCurrency());
        if (partnerUpdateDto.getCustomerType() != null) partnerReadDto.setCustomerType(partnerUpdateDto.getCustomerType());
        return partnerReadDto;
    }
}
