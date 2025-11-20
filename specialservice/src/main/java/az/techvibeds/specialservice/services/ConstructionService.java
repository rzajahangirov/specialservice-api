package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectCreateDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectReadDto;

public interface ConstructionService {
    ConstructionDto getConstructionPageData(String email);

    ConstructionProjectReadDto createConstructionProject(String userEmail, ConstructionProjectCreateDto dto);
}
