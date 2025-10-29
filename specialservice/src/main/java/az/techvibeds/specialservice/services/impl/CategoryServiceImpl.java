package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.category.CategoryCreateDto;
import az.techvibeds.specialservice.dtos.category.CategoryReadDto;
import az.techvibeds.specialservice.dtos.category.CategoryUpdateDto;
import az.techvibeds.specialservice.models.Category;
import az.techvibeds.specialservice.repositories.CategoryRepository;
import az.techvibeds.specialservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }
    @Override
    public CategoryReadDto create(CategoryCreateDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        // products null qalacaq
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryReadDto.class);
    }

    @Override
    public CategoryReadDto update(CategoryUpdateDto dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryReadDto.class);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryReadDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryReadDto.class);
    }

    @Override
    public List<CategoryReadDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryReadDto.class))
                .collect(Collectors.toList());
    }
}
