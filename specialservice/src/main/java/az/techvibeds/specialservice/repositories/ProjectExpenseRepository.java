package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.ProjectExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectExpenseRepository extends JpaRepository<ProjectExpense, Long> {
}
