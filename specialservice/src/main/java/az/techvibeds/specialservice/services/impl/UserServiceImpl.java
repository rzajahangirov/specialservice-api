package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.auth.RegisterDto;
import az.techvibeds.specialservice.dtos.auth.UserLoggedDto;
import az.techvibeds.specialservice.models.User;
import az.techvibeds.specialservice.repositories.UserRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.RoleService;
import az.techvibeds.specialservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String name) {
        User findUser = userRepository.findByEmail(name).orElseThrow();
        return findUser;
    }

    @Override
    public void register(RegisterDto registerDto) {
        User user = modelMapper.map(registerDto, User.class);
        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public UserLoggedDto getLoggedUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        UserLoggedDto userLoggedDto = modelMapper.map(user, UserLoggedDto.class);
        String role = "user";
        userLoggedDto.setActive(true);
        userLoggedDto.setRoleName(role);
        return userLoggedDto;
    }
}
