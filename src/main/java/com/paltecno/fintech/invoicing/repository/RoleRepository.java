package com.paltecno.fintech.invoicing.repository;

import com.paltecno.fintech.invoicing.domain.Role;

import java.util.Collection;

public interface RoleRepository <T extends Role>{
    /* Basic CRUD OPERATIONS */
    T create (T data);
    Collection<T> list(int page,int pageSize);
    T get(T data);
    T update(T data);
    Boolean delete(Long id);

    /* More complex Operations */
    void addRoleToUser(Long userId, String roleName);
}
