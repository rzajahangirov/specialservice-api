package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.User;
import az.techvibeds.specialservice.repositories.UserRepository;
import az.techvibeds.specialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public User findByEmail(String name) {
        User findUser = userRepository.findByEmail(name);
        return findUser;
    }
}
