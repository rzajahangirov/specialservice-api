package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseReadDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseUpdateDto;
import az.techvibeds.specialservice.models.IndirectExpense;

import java.util.List;

public interface IndirectExpenseService {
    IndirectExpenseReadDto create(IndirectExpenseCreateDto dto, String userEmail);
    IndirectExpenseReadDto update(Long id, IndirectExpenseUpdateDto dto, String userEmail);
    void delete(Long id, String userEmail);
    List<IndirectExpenseReadDto> getAll(String userEmail);
    IndirectExpense getById(Long id);
    List<IndirectExpenseReadDto> filterByCategory(String category, String userEmail);
    List<IndirectExpenseReadDto> search(String keyword, String userEmail);
}
