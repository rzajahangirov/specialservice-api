package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findAllByCompanyId(Long id);
}
