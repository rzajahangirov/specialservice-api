package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.IndirectExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndirectExpenseRepository extends JpaRepository<IndirectExpense, Long> {
}
