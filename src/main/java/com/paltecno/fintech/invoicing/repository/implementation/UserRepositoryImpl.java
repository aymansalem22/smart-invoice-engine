package com.paltecno.fintech.invoicing.repository.implementation;

import com.paltecno.fintech.invoicing.domain.Role;
import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.exception.ApiException;
import com.paltecno.fintech.invoicing.repository.RoleRepository;
import com.paltecno.fintech.invoicing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;


import static com.paltecno.fintech.invoicing.enumeration.RoleType.ROLE_USER;
import static com.paltecno.fintech.invoicing.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {
    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");

        //if not unique then save new user
        try
        {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            //add role to the user

            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            //send verification URL
            // Save URL in verification table
            //Send email to user with verification URL
            //Return the newly created user
            //if any errors, throw exception with proper message
        }
        catch(EmptyResultDataAccessException exception) {

        }
        catch(Exception exception){

        }


        return null;
    }




    @Override
    public Collection list(int page, int pageSize) {
        return List.of();
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email",email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
    }
}
