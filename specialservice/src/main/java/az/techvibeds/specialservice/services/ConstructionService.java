package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.construction.ConstructionDto;

public interface ConstructionService {
    ConstructionDto getConstructionPageData(String email);
}
