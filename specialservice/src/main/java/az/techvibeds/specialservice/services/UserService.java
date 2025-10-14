package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.auth.RegisterDto;
import az.techvibeds.specialservice.dtos.auth.UserLoggedDto;
import az.techvibeds.specialservice.models.User;

public interface UserService {
    User findByEmail(String name);

    UserLoggedDto getLoggedUserInfo(String email);

    void register(RegisterDto registerDto);

    User findUserById(Long userId);


}
