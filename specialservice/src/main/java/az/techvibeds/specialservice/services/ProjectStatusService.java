package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusCreateDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusReadDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusUpdateDto;

import java.util.List;

public interface ProjectStatusService {
    ProjectStatusReadDto createProjectStatus(ProjectStatusCreateDto projectStatusCreateDto);

    ProjectStatusReadDto getProjectStatusById(Long id);

    List<ProjectStatusReadDto> getAllProjectStatuses();

    ProjectStatusReadDto updateProjectStatus(Long id, ProjectStatusUpdateDto dto);

    void deleteProjectStatus(Long id);
}
