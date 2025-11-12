package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.repositories.ConstructionProjectRepository;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionProjectServiceImpl implements ConstructionProjectService {
    private final ConstructionProjectRepository constructionProjectRepository;
    @Override
    public List<ConstructionProject> getAllByCompanyId(Long companyId) {
        return constructionProjectRepository.findAllByCompany_Id(companyId);
    }
}
