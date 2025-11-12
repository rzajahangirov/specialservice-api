package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.ConstructionProject;

import java.util.List;

public interface ConstructionProjectService {
    List<ConstructionProject> getAllByCompanyId(Long companyId);
}
