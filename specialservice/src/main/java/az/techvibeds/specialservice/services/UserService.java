package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.User;

public interface UserService {
    User findByEmail(String name);
}
