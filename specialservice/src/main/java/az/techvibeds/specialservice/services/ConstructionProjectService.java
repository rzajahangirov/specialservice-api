package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectDto;
import az.techvibeds.specialservice.models.ConstructionProject;

import java.security.Principal;
import java.util.List;

public interface ConstructionProjectService {
    List<ConstructionProject> getAllByCompanyId(Long companyId);

    List<ConstructionProjectDto> mapToConstructionProjectDto(List<ConstructionProject> constructionProjectList);
    ConstructionProject findById(Long id);

    List<ConstructionProjectDto> getFilteredProjects(Long statusId, String projectName, String userEmail);
}
