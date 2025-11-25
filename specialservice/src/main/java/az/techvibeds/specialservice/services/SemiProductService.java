package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.semiproduct.SemiProductCreateDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductReadDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductUpdateDto;
import az.techvibeds.specialservice.models.SemiProduct;

import java.util.List;

public interface SemiProductService {
    SemiProductReadDto create(SemiProductCreateDto dto, String userEmail);
    SemiProductReadDto update(Long id, SemiProductUpdateDto dto, String userEmail);
    void delete(Long id, String userEmail);
    List<SemiProductReadDto> getAll(String userEmail);
    SemiProduct getById(Long id);
    List<SemiProductReadDto> filterByStatus(String status, String userEmail);
    List<SemiProductReadDto> search(String keyword, String userEmail);

}
