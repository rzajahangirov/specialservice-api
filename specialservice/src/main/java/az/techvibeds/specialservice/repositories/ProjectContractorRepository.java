package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.ProjectContractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectContractorRepository extends JpaRepository<ProjectContractor, Long> {
    List<ProjectContractor> findAllByCompany_Id(Long companyId);
}
