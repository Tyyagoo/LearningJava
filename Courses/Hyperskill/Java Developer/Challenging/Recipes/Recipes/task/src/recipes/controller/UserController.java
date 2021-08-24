package recipes.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import recipes.model.User;
import recipes.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(path = "${v1API}/register")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        System.out.printf("Trying to register user:%n%s%n", userDto);
        if (validateEmail(userDto.email)) {
            if (userService.getUserByEmail(userDto.email).isEmpty()) {
                userService.saveUser(userDto.getUserOfDto(passwordEncoder));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean validateEmail(String email) {
        if (email.contains("@")) {
            String[] split = email.split("@");
            if (split.length == 2) {
                if (split[1].contains(".")) {
                    String[] domainSplit = split[1].split("\\.");
                    if (domainSplit.length == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        @Email
        @NotBlank
        private String email;

        @Size(min = 8)
        @NotBlank
        private String password;

        public User getUserOfDto(BCryptPasswordEncoder passwordEncoder) {
            return new User(null, email, passwordEncoder.encode(password), List.of());
        }
    }
}
