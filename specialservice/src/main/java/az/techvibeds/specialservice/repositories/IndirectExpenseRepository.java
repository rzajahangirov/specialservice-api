package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.enums.ExpenseCategory;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.IndirectExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndirectExpenseRepository extends JpaRepository<IndirectExpense, Long> {
    List<IndirectExpense> findAllByCompany(Company company);

    List<IndirectExpense> findAllByCompany_IdAndCategory(Long id, ExpenseCategory expenseCategory);
    @Query("SELECT ie FROM IndirectExpense ie " +
            "WHERE ie.company.id = :companyId " +
            "AND (CAST(ie.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR LOWER(ie.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<IndirectExpense> searchByIdOrName(@Param("keyword") String keyword,
                                           @Param("companyId") Long companyId);

}
