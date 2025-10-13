package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.User;
import az.techvibeds.specialservice.repositories.CompanyRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final UserService userService;

    @Override
    public Company findByUserEmail(String name) {
        User findUser = userService.findByEmail(name);
        Company company = companyRepository.findByUserId(findUser.getId());
        return company;
    }
}
