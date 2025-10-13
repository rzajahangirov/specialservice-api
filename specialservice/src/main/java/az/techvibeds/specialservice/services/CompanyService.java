package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Company;

public interface CompanyService {
    Company findByUserEmail(String name);
}
