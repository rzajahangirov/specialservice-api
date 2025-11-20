package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.balace.CreateBalanceDto;
import az.techvibeds.specialservice.dtos.partner.*;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    public PartnerReadDto createCustomer(PartnerCreateDto dto, String userEmail) {
        return createPartner(dto, PartnerType.CUSTOMER, userEmail);
    }

    @Override
    public PartnerReadDto createSupplier(PartnerCreateDto dto, String userEmail) {
        return createPartner(dto, PartnerType.SUPPLIER, userEmail);
    }

    private PartnerReadDto createPartner(PartnerCreateDto dto, PartnerType type, String userEmail) {
        Partner partner = mapCreateDtoToPartner(dto);
        partner.setPartnerType(type);
        partner.setCompany(companyService.findByUserEmail(userEmail));

         partnerRepository.save(partner);


        Balance balance = createBalanceForPartner(dto, partner);
        partner.setBalance(balance);


        partnerRepository.save(partner);

        return mapToReadDto(partner);
    }


    private Partner mapCreateDtoToPartner(PartnerCreateDto dto) {
        Partner partner = new Partner();
        partner.setName(dto.getName());
        partner.setContactPerson(dto.getContactPerson());
        partner.setEmail(dto.getEmail());
        partner.setPhone(dto.getPhone());
        return partner;
    }

    private Balance createBalanceForPartner(PartnerCreateDto dto, Partner partner) {
        CreateBalanceDto balanceDto = new CreateBalanceDto();
        balanceDto.setPartner(partner);
        balanceDto.setAmount(dto.getBalance());
        balanceDto.setCurrencyType(dto.getCurrency());
        return balanceService.createBalance(balanceDto);
    }


    @Override
    public List<PartnerDto> getAllPartnersByCompany(String userEmail) {
        Company company = companyService.findByUserEmail(userEmail);
        List<Partner> partners = partnerRepository.findAllByCompanyId(company.getId());
        return mapToDtoList(partners);
    }


    @Override
    public void importFromExcel(MultipartFile file, String userEmail) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Map<String, String> currencyMap = Map.of(
                "₼", "manat",
                "$", "USD",
                "€", "EUR"
        );

        Company company = companyService.findByUserEmail(userEmail);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;


            Partner partner = new Partner();
            partner.setName(row.getCell(0).getStringCellValue());
            partner.setContactPerson(row.getCell(1).getStringCellValue());
            partner.setEmail(row.getCell(2).getStringCellValue());
            partner.setPhone(row.getCell(3).getStringCellValue());
            partner.setPartnerType(parsePartnerTypeFromRow(row.getCell(6).getStringCellValue()));
            partner.setCompany(company);


            partnerRepository.save(partner);


            Balance balance = parseBalanceFromRow(row, partner, currencyMap);
            partner.setBalance(balance);


            partnerRepository.save(partner);
        }
    }

    private Balance parseBalanceFromRow(Row row, Partner partner, Map<String, String> currencyMap) throws Exception {

        Cell balanceCell = row.getCell(4);
        if (balanceCell.getCellType() != CellType.NUMERIC) {
            throw new Exception("The Excel columns are not correct. Balance must be numeric.");
        }
        double amount = balanceCell.getNumericCellValue();


        Cell currencyCell = row.getCell(5);
        if (currencyCell.getCellType() != CellType.STRING) {
            throw new Exception("The Excel columns are not correct. Currency must be text.");
        }
        String currencySymbol = currencyCell.getStringCellValue().trim();
        String currencyType = currencyMap.getOrDefault(currencySymbol, "UNKNOWN");

        Balance balance = new Balance();
        balance.setAmount(BigDecimal.valueOf(amount));
        balance.setCurrencyType(currencyType);
        balance.setPartner(partner);

        return balance;
    }

    private PartnerType parsePartnerTypeFromRow(String type) throws Exception {
        if ("CUSTOMER".equalsIgnoreCase(type)) {
            return PartnerType.CUSTOMER;
        } else if ("SUPPLIER".equalsIgnoreCase(type)) {
            return PartnerType.SUPPLIER;
        } else {
            throw new Exception("Unknown partner type: " + type);
        }
    }



    @Override
    public PartnerReadDto updatePartner(PartnerUpdateDto dto, String userEmail) throws Exception {
        Partner partner = partnerRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        Company company = companyService.findByUserEmail(userEmail);
        if (company == partner.getCompany()) {
            if (dto.getName() != null) partner.setName(dto.getName());
            if (dto.getContactPerson() != null) partner.setContactPerson(dto.getContactPerson());
            if (dto.getEmail() != null) partner.setEmail(dto.getEmail());
            if (dto.getPhone() != null) partner.setPhone(dto.getPhone());

            if (dto.getBalance() != null || dto.getCurrency() != null) {
                Balance balance = new Balance();
                if (dto.getBalance() != null) balance.setAmount(dto.getBalance());
                if (dto.getCurrency() != null) balance.setCurrencyType(dto.getCurrency());
                partner.setBalance(balance);
            }

            if (dto.getCustomerType() != null) {
                partner.setPartnerType(parsePartnerTypeFromRow(dto.getCustomerType()));
            }

            partnerRepository.save(partner);

            return mapToReadDto(partner);
        }else {
            throw new Exception("Company does not match");
        }
    }


    @Override
    public void delete(Long id, String userEmail) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        Company company = companyService.findByUserEmail(userEmail);
        if (company == partner.getCompany()) {
            partnerRepository.delete(partner);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

    @Override
    public ByteArrayInputStream exportToExcel(String userEmail) throws Exception {
        Company company = companyService.findByUserEmail(userEmail);
        List<Partner> partners = partnerRepository.findAllByCompany(company);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Partners");

            // Başlıqlar
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Name", "Contact Person", "Email", "Phone", "Balance", "Currency", "Partner Type"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Partner partner : partners) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(partner.getName());
                row.createCell(1).setCellValue(partner.getContactPerson());
                row.createCell(2).setCellValue(partner.getEmail());
                row.createCell(3).setCellValue(partner.getPhone());

                // Balance məlumatı varsa
                Balance balance = partner.getBalance();
                if (balance != null) {
                    row.createCell(4).setCellValue(balance.getAmount().doubleValue());
                    row.createCell(5).setCellValue(balance.getCurrencyType());
                } else {
                    row.createCell(4).setCellValue(0);
                    row.createCell(5).setCellValue("UNKNOWN");
                }

                row.createCell(6).setCellValue(partner.getPartnerType().toString());
            }

            // Avtomatik sütun eni
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Override
    public PartnerTypeDto getPartnerTypes() {
        PartnerTypeDto partnerTypeDto = new PartnerTypeDto();
        partnerTypeDto.setPartnerTypes(
                Arrays.stream(PartnerType.values())
                        .map(Enum::name)
                        .toList()
        );
        return partnerTypeDto;
    }

    @Override
    public List<PartnerDto> findByPartnerTypeAndCompany_Id(String type, String email) throws Exception {
        List<Partner> partners = partnerRepository.findAllByCompanyIdAndPartnerType(companyService.findByUserEmail(email).getId(),parsePartnerTypeFromRow(type));
        return mapToDtoList(partners);
    }


    private PartnerReadDto mapToReadDto(Partner partner) {
        PartnerReadDto readDto = new PartnerReadDto();
        readDto.setId(partner.getId());
        readDto.setName(partner.getName());
        readDto.setContactPerson(partner.getContactPerson());
        readDto.setEmail(partner.getEmail());
        readDto.setPhone(partner.getPhone());
        readDto.setBalance(partner.getBalance().getAmount());
        readDto.setCurrency(partner.getBalance().getCurrencyType());
        readDto.setPartnerType(partner.getPartnerType().name());
        return readDto;
    }
    private List<PartnerDto> mapToDtoList(List<Partner> partners) {
        List<PartnerDto> partnerDtos = new ArrayList<>();

        for (Partner partner : partners) {
            PartnerDto dto = new PartnerDto();
            dto.setId(partner.getId());
            dto.setName(partner.getName());
            dto.setContactPerson(partner.getContactPerson());
            dto.setEmail(partner.getEmail());
            dto.setPhone(partner.getPhone());
            dto.setBalance(partner.getBalance().getAmount());
            dto.setCurrency(partner.getBalance().getCurrencyType());

            String partnerType = partner.getPartnerType().toString();
            dto.setPartnerType(partnerType.substring(0, 1).toUpperCase() + partnerType.substring(1).toLowerCase());

            partnerDtos.add(dto);
        }
        return partnerDtos;
    }
}
