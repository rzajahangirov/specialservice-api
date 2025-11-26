package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectstage.ProjectStageCreateDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageReadDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageUpdateDto;
import az.techvibeds.specialservice.enums.StageStatus;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.models.ProjectStage;
import az.techvibeds.specialservice.repositories.ProjectStageRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import az.techvibeds.specialservice.services.ProjectStageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStageServiceImpl implements ProjectStageService {
    private final ProjectStageRepository projectStageRepository;
    private final ConstructionProjectService constructionProjectService;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;

    @Override
    public List<ProjectStageDto> getActiveStagesFromProjects(List<ConstructionProject> constructionProjectList) {
        List<ProjectStageDto> activeStages = new ArrayList<>();
        for (ConstructionProject constructionProject : constructionProjectList) {
            List<ProjectStage> projectStageList = constructionProject.getStages();
            for (ProjectStage projectStage : projectStageList) {
                if (projectStage.getStatus().name().equals("ACTIVE")) {
                    ProjectStageDto projectStageDto = modelMapper.map(projectStage, ProjectStageDto.class);
                    projectStageDto.setProjectName(constructionProject.getName());
                    activeStages.add(projectStageDto);
                }
            }
        }
        return activeStages;
    }

    @Override
    public ProjectStageReadDto create(Long projectId, ProjectStageCreateDto dto) {
        if (dto.getStageNumber() < 0){
            throw new IllegalArgumentException("Invalid stage number");
        }
        ConstructionProject project = constructionProjectService.findById(projectId);

        ProjectStage stage = modelMapper.map(dto, ProjectStage.class);
        stage.setProject(project);

        String statusName = dto.getStatusName();
        if (statusName != null) {
            statusName = statusName.toUpperCase();
            if ("ACTIVE".equals(statusName)) {
                stage.setStatus(StageStatus.ACTIVE);
            } else if ("COMPLETED".equals(statusName)) {
                stage.setStatus(StageStatus.COMPLETED);
            } else if ("PENDING".equals(statusName)) {
                stage.setStatus(StageStatus.PENDING);
            } else {
                stage.setStatus(null);
            }
        } else {
            throw new RuntimeException("No status name provided");
        }


        projectStageRepository.save(stage);

        return mapToRead(stage);
    }

    @Override
    public List<ProjectStageReadDto> getAllByProject(Long projectId, String userEmail) {
        List<ProjectStage> projectStageList = projectStageRepository.findAllByProjectId(projectId);
        List<ProjectStageReadDto> projectStageReadDtos = new ArrayList<>();
        for (ProjectStage projectStage : projectStageList) {
            if (projectStage.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
                ProjectStageReadDto projectStageReadDto = mapToRead(projectStage);
                projectStageReadDtos.add(projectStageReadDto);
            }
        }
        return projectStageReadDtos;
    }

    @Override
    public ProjectStageReadDto getById(Long id, String userEmail) {
        ProjectStage stage = projectStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage can not find"));
        if (stage.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
            return mapToRead(stage);
        }else{
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public ProjectStageReadDto update(Long id, ProjectStageUpdateDto dto, String userEmail) {
        if (dto.getStageNumber() < 0){
            throw new IllegalArgumentException("Invalid stage number");
        }
        if (dto.getProgressPercentage() < 0){
            throw new IllegalArgumentException("Invalid progress percentage");
        }
        ProjectStage stage = projectStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage can not find"));
        if (stage.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
            // PROJECT DƏYİŞDİRİLMİR
            stage.setName(dto.getName());
            stage.setStageNumber(dto.getStageNumber());
            stage.setProgressPercentage(dto.getProgressPercentage());
            try {
                stage.setStatus(StageStatus.valueOf(dto.getStatusName().toUpperCase()));
            } catch (Exception e) {
                stage.setStatus(null);
            }

            projectStageRepository.save(stage);

            return mapToRead(stage);

        }else {
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public void delete(Long id, String userEmail) {
        ProjectStage projectStage = projectStageRepository.findById(id).orElseThrow(() -> new RuntimeException("Stage can not find"));
        if (projectStage.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
            projectStageRepository.delete(projectStage);
        }else {
            throw new RuntimeException("Access denied");
        }
    }
    private ProjectStageReadDto mapToRead(ProjectStage projectStage) {
        ProjectStageReadDto readDto = modelMapper.map(projectStage, ProjectStageReadDto.class);
        readDto.setStagesStatus(projectStage.getStatus().name());
        return readDto;
    }
}
