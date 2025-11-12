package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.ConstructionProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstructionProjectRepository extends JpaRepository<ConstructionProject, Long> {
    List<ConstructionProject> findAllByCompany_Id(Long companyId);
}
