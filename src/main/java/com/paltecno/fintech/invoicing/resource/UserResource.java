package com.paltecno.fintech.invoicing.resource;

import com.paltecno.fintech.invoicing.domain.HttpResponse;
import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.dto.UserDTO;
import com.paltecno.fintech.invoicing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(String email, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return null;
    }


    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
     UserDTO userDTO = userService.createUser(user);
     return ResponseEntity.created(getUri()).body(
             HttpResponse.builder()
                     .timeStamp(now().toString())
                     .data(of("user",userDTO))
                     .message("User created")
                     .status(CREATED)
                     .statusCode(CREATED.value())
                     .build());

    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }

}
