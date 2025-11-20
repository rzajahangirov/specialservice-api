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

    ProjectContractorReadDto getById(Long id);

    List<ProjectContractorReadDto> getAll();

    ProjectContractorReadDto update(Long id, ProjectContractorUpdateDto dto);

    void delete(Long id);
}
