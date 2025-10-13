package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
