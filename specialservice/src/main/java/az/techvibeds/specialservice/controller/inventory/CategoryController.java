package az.techvibeds.specialservice.controller.inventory;

import az.techvibeds.specialservice.dtos.category.CategoryCreateDto;
import az.techvibeds.specialservice.dtos.category.CategoryReadDto;
import az.techvibeds.specialservice.dtos.category.CategoryUpdateDto;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryReadDto> create(@RequestBody CategoryCreateDto dto){
        CategoryReadDto created = categoryService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryReadDto> update(@RequestBody CategoryUpdateDto dto){
        CategoryReadDto updated = categoryService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReadDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryReadDto>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }
}
