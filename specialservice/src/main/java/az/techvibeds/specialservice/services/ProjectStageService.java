package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.projectstage.ProjectStageCreateDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageReadDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageUpdateDto;
import az.techvibeds.specialservice.models.ConstructionProject;

import java.util.List;

public interface ProjectStageService {
    List<ProjectStageDto> getActiveStagesFromProjects(List<ConstructionProject> constructionProjectList);

    ProjectStageReadDto create(Long projectId, ProjectStageCreateDto dto);

    List<ProjectStageReadDto> getAllByProject(Long projectId, String userEmail);

    ProjectStageReadDto getById(Long id, String userEmail);

    ProjectStageReadDto update(Long id, ProjectStageUpdateDto dto, String userEmail);

    void delete(Long id, String userEmail);
}
