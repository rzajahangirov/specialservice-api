package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectDto;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.repositories.ConstructionProjectRepository;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConstructionProjectServiceImpl implements ConstructionProjectService {
    private final ConstructionProjectRepository constructionProjectRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ConstructionProject> getAllByCompanyId(Long companyId) {
        return constructionProjectRepository.findAllByCompany_Id(companyId);
    }

    @Override
    public List<ConstructionProjectDto> mapToConstructionProjectDto(List<ConstructionProject> constructionProjectList) {
        return constructionProjectList
                .stream()
                .map(constructionProject -> {
                    ConstructionProjectDto dto = modelMapper.map(constructionProject, ConstructionProjectDto.class);
                    dto.setProjectStatus(constructionProject.getStatus().getName());
                    return dto;})
                .collect(Collectors.toList());
    }
}
