package com.paltecno.fintech.invoicing.repository;

import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.dto.UserDTO;

import java.util.Collection;

public interface UserRepository <T extends User>{
    /* Basic CRUD OPERATIONS */
    T create (T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update (T data);
    Boolean delete(Long id);
    User getUserByEmail(String email);

    void sendVerificationCode(UserDTO user);


    /* More complex Operations */

}
