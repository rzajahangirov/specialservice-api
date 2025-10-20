package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.Category;
import az.techvibeds.specialservice.repositories.CategoryRepository;
import az.techvibeds.specialservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
