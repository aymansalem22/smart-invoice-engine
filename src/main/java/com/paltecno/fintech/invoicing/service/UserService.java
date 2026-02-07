package com.paltecno.fintech.invoicing.service;

import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.dto.UserDTO;

public interface UserService {
    //concept data transfer pattern for that create package dto
    UserDTO createUser(User user);
    UserDTO getUserByEmail(String email);
}
