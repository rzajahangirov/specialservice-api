package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseReadDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseUpdateDto;
import az.techvibeds.specialservice.enums.CurrencyType;
import az.techvibeds.specialservice.enums.ExpenseCategory;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.IndirectExpense;
import az.techvibeds.specialservice.repositories.IndirectExpenseRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.IndirectExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndirectExpenseServiceImpl implements IndirectExpenseService {
    private final IndirectExpenseRepository indirectExpenseRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Override
    public IndirectExpenseReadDto create(IndirectExpenseCreateDto dto, String userEmail) {

        Company company = companyService.findByUserEmail(userEmail);

        IndirectExpense expense = modelMapper.map(dto, IndirectExpense.class);

        expense.setCurrency(CurrencyType.valueOf(dto.getCurrency().toUpperCase()));
        expense.setCategory(ExpenseCategory.valueOf(dto.getCategory().toUpperCase()));

        expense.setCompany(company);

        IndirectExpense saved = indirectExpenseRepository.save(expense);

        return mapToRead(saved);
    }

    @Override
    public IndirectExpenseReadDto update(Long id, IndirectExpenseUpdateDto dto, String userEmail) {

        IndirectExpense expense = getById(id);

        Company userCompany = companyService.findByUserEmail(userEmail);
        if (!expense.getCompany().getId().equals(userCompany.getId())) {
            throw new RuntimeException("Access denied");
        }

        modelMapper.map(dto, expense);

        expense.setCurrency(CurrencyType.valueOf(dto.getCurrency().toUpperCase()));
        expense.setCategory(ExpenseCategory.valueOf(dto.getCategory().toUpperCase()));

        IndirectExpense updated = indirectExpenseRepository.save(expense);

        return mapToRead(updated);
    }

    @Override
    public void delete(Long id, String userEmail) {

        IndirectExpense expense = getById(id);

        Company userCompany = companyService.findByUserEmail(userEmail);
        if (!expense.getCompany().getId().equals(userCompany.getId())) {
            throw new RuntimeException("Access denied!");
        }

        indirectExpenseRepository.delete(expense);
    }

    @Override
    public List<IndirectExpenseReadDto> getAll(String userEmail) {

        Company company = companyService.findByUserEmail(userEmail);
        List<IndirectExpense> list = indirectExpenseRepository.findAllByCompany(company);

        List<IndirectExpenseReadDto> result = new ArrayList<>();
        for (IndirectExpense e : list) {
            result.add(mapToRead(e));
        }

        return result;
    }

    @Override
    public IndirectExpense getById(Long id) {
        return indirectExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("IndirectExpense not found!"));
    }
    @Override
    public List<IndirectExpenseReadDto> filterByCategory(String category, String userEmail) {

        if (category == null || category.isBlank()) {
            throw new RuntimeException("Category boş ola bilməz!");
        }

        ExpenseCategory expenseCategory;
        try {
            expenseCategory = ExpenseCategory.valueOf(category.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Unknown expense category: " + category);
        }

        Company company = companyService.findByUserEmail(userEmail);

        List<IndirectExpense> list =
                indirectExpenseRepository.findAllByCompany_IdAndCategory(company.getId(), expenseCategory);

        List<IndirectExpenseReadDto> result = new ArrayList<>();
        for (IndirectExpense e : list) {
            result.add(mapToRead(e));
        }
        return result;
    }

    @Override
    public List<IndirectExpenseReadDto> search(String keyword, String userEmail) {

        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        Company company = companyService.findByUserEmail(userEmail);

        List<IndirectExpense> list =
                indirectExpenseRepository.searchByIdOrName(keyword, company.getId());

        List<IndirectExpenseReadDto> result = new ArrayList<>();
        for (IndirectExpense e : list) {
            result.add(mapToRead(e));
        }
        return result;
    }

    private IndirectExpenseReadDto mapToRead(IndirectExpense expense) {
        IndirectExpenseReadDto dto = modelMapper.map(expense, IndirectExpenseReadDto.class);

        dto.setCurrency(expense.getCurrency().name());
        dto.setCategory(expense.getCategory().name());

        return dto;
    }
}
