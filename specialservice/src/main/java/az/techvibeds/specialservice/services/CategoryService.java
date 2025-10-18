package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Category;

public interface CategoryService {
    Category findByName(String stringCellValue);
}
