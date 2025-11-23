package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorReadDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorUpdateDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorCreateDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorDto;

import java.security.Principal;
import java.util.List;

public interface ProjectContractorService {
    List<ProjectContractorDto> getAllByCompanyId(Long id);

    ProjectContractorReadDto create(ProjectContractorCreateDto dto, Principal principal);

    ProjectContractorReadDto getById(Long id, String userEmail);

    List<ProjectContractorReadDto> getAll(String userEmail);

    ProjectContractorReadDto update(Long id, ProjectContractorUpdateDto dto, String userEmail);

    void delete(Long id, String userEmail);

    List<ProjectContractorDto> getFilteredContractors(String contractorName, String userEmail);
}
