package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import az.techvibeds.specialservice.services.ConstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionServiceImpl implements ConstructionService {
    private final ConstructionProjectService constructionProjectService;
    private final CompanyService companyService;

    @Override
    public ConstructionDto getConstructionPageData(String email) {
        ConstructionDto constructionDto = new ConstructionDto();
        Company company = companyService.findByUserEmail(email);
        List<ConstructionProject> constructionProjectList = constructionProjectService.getAllByCompanyId(company.getId());
        constructionDto.setCountOfProjects(constructionProjectList.size());
        Integer countOfActiveProjects = 0;
        Integer countOfCompletedProjects = 0;
        for (ConstructionProject constructionProject : constructionProjectList) {
            LocalDate endDate = constructionProject.getEndDate().plusMonths(6);
            if (constructionProject.getStatus().getName().toUpperCase().equals("ACTIVE")) {
                countOfActiveProjects++;
            }else if (constructionProject.getStatus().getName().toUpperCase().equals("COMPLETED") && LocalDate.now().isBefore(endDate)  ) {
                countOfCompletedProjects++;
            }
        }
        constructionDto.setCountOfActiveProjects(countOfActiveProjects);
        constructionDto.setCountOfCompletedProjects(countOfCompletedProjects);
        BigDecimal budget = company.getBudget();
        BigDecimal usedBudget = company.getUsedBudget();

        BigDecimal percentage = usedBudget
                .divide(budget, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        constructionDto.setBudgetUsagePercentage(percentage.doubleValue());

        return null;
    }
}
