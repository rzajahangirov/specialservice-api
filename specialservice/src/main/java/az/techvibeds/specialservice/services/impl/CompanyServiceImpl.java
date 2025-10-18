package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.company.CompanyCreateDto;
import az.techvibeds.specialservice.dtos.company.CompanyReadDto;
import az.techvibeds.specialservice.dtos.company.CompanyUpdateDto;
import az.techvibeds.specialservice.dtos.module.ModuleReadDto;
import az.techvibeds.specialservice.exceptions.ResourceNotFoundException;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.User;
import az.techvibeds.specialservice.models.Module;
import az.techvibeds.specialservice.repositories.CompanyRepository;
import az.techvibeds.specialservice.repositories.ModuleRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final ModuleRepository moduleRepository;

    @Override
    public Company findByUserEmail(String name) {
        User findUser = userService.findByEmail(name);
        Company company = companyRepository.findByUsers_Id(findUser.getId());
        return company;
    }
    @Override
    public CompanyReadDto create(CompanyCreateDto createDto) {
        Company company = new Company();
        company.setName(createDto.getName());
        company.setExpressDate(createDto.getExpressDate());
        if (createDto.getModuleIds() != null && !createDto.getModuleIds().isEmpty()) {
            Set<Module> modules = createDto.getModuleIds().stream()
                    .map(id -> moduleRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id)))
                    .collect(Collectors.toSet());
            company.setModules(modules);
        }
        Company saved = companyRepository.save(company);
        return mapToReadDto(saved);
    }

    @Override
    public CompanyReadDto update(CompanyUpdateDto updateDto) {
        Company company = companyRepository.findById(updateDto.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        if (updateDto.getName() != null) company.setName(updateDto.getName());
        if (updateDto.getExpressDate() != null) company.setExpressDate(updateDto.getExpressDate());
        if (updateDto.getModuleIds() != null) {
            Set<Module> modules = updateDto.getModuleIds().stream()
                    .map(id -> moduleRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id)))
                    .collect(Collectors.toSet());
            company.setModules(modules);
        }
        Company saved = companyRepository.save(company);
        return mapToReadDto(saved);
    }

    @Override
    public void delete(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        companyRepository.delete(company);
    }

    @Override
    public CompanyReadDto getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return mapToReadDto(company);
    }

    @Override
    public List<CompanyReadDto> getAll() {
        return companyRepository.findAll().stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    private CompanyReadDto mapToReadDto(Company company) {
        CompanyReadDto dto = new CompanyReadDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setExpressDate(company.getExpressDate());
        if (company.getModules() != null) {
            dto.setModules(company.getModules().stream()
                    .map(m -> ModuleReadDto.builder().id(m.getId()).name(m.getName()).build())
                    .collect(Collectors.toSet()));
        }
        // Users and Products intentionally omitted for brevity/not required for CRUD basics
        return dto;
    }
}
