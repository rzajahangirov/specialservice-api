package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorReadDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorUpdateDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorCreateDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.ProjectContractor;
import az.techvibeds.specialservice.repositories.ProjectContractorRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ProjectContractorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectContractorServiceImpl implements ProjectContractorService {
    private final ProjectContractorRepository projectContractorRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;


    @Override
    public List<ProjectContractorDto> getAllByCompanyId(Long id) {
        return  projectContractorRepository.findAllByCompany_Id(id)
                .stream()
                .map(projectContractor -> {
                    ProjectContractorDto dto = modelMapper.map(projectContractor, ProjectContractorDto.class);
                    dto.setProjectCount(projectContractor.getConstructionProjects().size());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public ProjectContractorReadDto create(ProjectContractorCreateDto dto, Principal principal) {

        ProjectContractor contractor = modelMapper.map(dto, ProjectContractor.class);

        contractor.setConstructionProjects(new ArrayList<>());

        Company company = companyService.findByUserEmail(principal.getName());
        contractor.setCompany(company);

        projectContractorRepository.save(contractor);

        return modelMapper.map(contractor, ProjectContractorReadDto.class);
    }

    @Override
    public ProjectContractorReadDto getById(Long id) {
        ProjectContractor contractor = projectContractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));

        return modelMapper.map(contractor, ProjectContractorReadDto.class);
    }

    @Override
    public List<ProjectContractorReadDto> getAll() {
        return projectContractorRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, ProjectContractorReadDto.class))
                .toList();
    }

    @Override
    public ProjectContractorReadDto update(Long id, ProjectContractorUpdateDto dto) {
        ProjectContractor contractor = projectContractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));

        contractor.setName(dto.getName());
        contractor.setContactPerson(dto.getContactPerson());
        contractor.setPhoneNumber(dto.getPhoneNumber());
        contractor.setEmail(dto.getEmail());

        projectContractorRepository.save(contractor);

        return modelMapper.map(contractor, ProjectContractorReadDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!projectContractorRepository.existsById(id)) {
            throw new RuntimeException("Contractor not found");
        }
        projectContractorRepository.deleteById(id);
    }
}
