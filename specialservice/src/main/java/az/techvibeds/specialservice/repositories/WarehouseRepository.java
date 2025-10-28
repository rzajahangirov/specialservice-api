package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByName(String name);

    List<WarehouseProduct> findByCompany_Id(Long companyId);

    Warehouse findByNameAndCompany(String name, Company company);

    List<Warehouse> findAllByCompanyId(Long companyId);
}
