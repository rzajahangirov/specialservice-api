package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.ProjectStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ProjectStageRepository extends JpaRepository<ProjectStage, Long> {
    List<ProjectStage> findAllByProjectId(Long projectId);
}
