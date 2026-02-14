package com.paltecno.fintech.invoicing.service.implementation;

import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.dto.UserDTO;
import com.paltecno.fintech.invoicing.dtomapper.UserDTOMapper;
import com.paltecno.fintech.invoicing.repository.UserRepository;
import com.paltecno.fintech.invoicing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {

        return UserDTOMapper.fromUser(userRepository.create(user));
    }
    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTOMapper.fromUser(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);
    }
}
