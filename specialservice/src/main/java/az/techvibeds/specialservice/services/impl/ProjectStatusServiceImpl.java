package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusCreateDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusReadDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusUpdateDto;
import az.techvibeds.specialservice.models.ProjectStatus;
import az.techvibeds.specialservice.repositories.ProjectStatusRepository;
import az.techvibeds.specialservice.services.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStatusServiceImpl implements ProjectStatusService {
    private final ProjectStatusRepository projectStatusRepository;
    private final ModelMapper modelMapper;

    @Override public ProjectStatusReadDto createProjectStatus(ProjectStatusCreateDto projectStatusCreateDto) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName(projectStatusCreateDto.getName());
        projectStatusRepository.save(projectStatus);
        return mapToReadDto(projectStatus); }

    @Override
    public ProjectStatusReadDto getProjectStatusById(Long id) {
        ProjectStatus projectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        return mapToReadDto(projectStatus);
    }

    @Override
    public List<ProjectStatusReadDto> getAllProjectStatuses() {
        return projectStatusRepository.findAll()
                .stream()
                .map(projectStatus -> modelMapper.map(projectStatus, ProjectStatusReadDto.class))
                .toList();
    }

    @Override
    public ProjectStatusReadDto updateProjectStatus(Long id, ProjectStatusUpdateDto dto) {
        ProjectStatus projectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        projectStatus.setName(dto.getName());
        projectStatusRepository.save(projectStatus);

        return mapToReadDto(projectStatus);
    }

    @Override
    public void deleteProjectStatus(Long id) {
        if (!projectStatusRepository.existsById(id)) {
            throw new RuntimeException("Status not found");
        }
        projectStatusRepository.deleteById(id);
    }

    private ProjectStatusReadDto mapToReadDto(ProjectStatus projectStatus) {
        return ProjectStatusReadDto.builder()
                .id(projectStatus.getId())
                .name(projectStatus.getName())
                .build();
    }
}
