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
    public ProjectContractorReadDto getById(Long id, String userEmail) {
        ProjectContractor contractor = projectContractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));
        if (contractor.getCompany() == companyService.findByUserEmail(userEmail)) {
            return modelMapper.map(contractor, ProjectContractorReadDto.class);
        }else {
            throw new RuntimeException("Access denied-> You are not allowed to access this contractor");
        }


    }

    @Override
    public List<ProjectContractorReadDto> getAll(String userEmail) {
        return projectContractorRepository.findAllByCompany_Id(companyService.findByUserEmail(userEmail).getId())
                .stream()
                .map(c -> modelMapper.map(c, ProjectContractorReadDto.class))
                .toList();
    }

    @Override
    public ProjectContractorReadDto update(Long id, ProjectContractorUpdateDto dto, String userEmail) {
        ProjectContractor contractor = projectContractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));
        if (contractor.getCompany() == companyService.findByUserEmail(userEmail)) {
            contractor.setName(dto.getName());
            contractor.setContactPerson(dto.getContactPerson());
            contractor.setPhoneNumber(dto.getPhoneNumber());
            contractor.setEmail(dto.getEmail());

            projectContractorRepository.save(contractor);

            return modelMapper.map(contractor, ProjectContractorReadDto.class);
        }else {
            throw new RuntimeException("Access denied-> You are not allowed to access this contractor");
        }

    }

    @Override
    public void delete(Long id, String userEmail) {
        ProjectContractor projectContractor = projectContractorRepository.findById(id).orElseThrow(() -> new RuntimeException("Contractor not found"));
        if ( projectContractor.getCompany() == companyService.findByUserEmail(userEmail)) {
            projectContractorRepository.delete(projectContractor);
        }
    }

    @Override
    public List<ProjectContractorDto> getFilteredContractors(String contractorName, String userEmail) {

        Long companyId = companyService.findByUserEmail(userEmail).getId();


        List<ProjectContractor> contractorList =
                projectContractorRepository.findAllByCompany_Id(companyId);

        List<ProjectContractor> filteredContractors = new ArrayList<>();


        if (contractorName != null && !contractorName.isEmpty()) {

            for (ProjectContractor contractor : contractorList) {

                boolean nameMatches =
                        contractor.getName().toLowerCase()
                                .contains(contractorName.toLowerCase());

                if (nameMatches) {
                    filteredContractors.add(contractor);
                }
            }

            return contractorToDto(filteredContractors);
        }

        return contractorToDto(contractorList);
    }
    private List<ProjectContractorDto> contractorToDto(List<ProjectContractor> contractorList) {
        return contractorList.stream()
                .map(c -> modelMapper.map(c, ProjectContractorDto.class))
                .toList();
    }


}
