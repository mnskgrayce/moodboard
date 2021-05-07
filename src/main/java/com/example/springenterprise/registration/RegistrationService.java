package com.example.springenterprise.registration;

import com.example.springenterprise.user.AppUser;
import com.example.springenterprise.user.AppUserRole;
import com.example.springenterprise.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isCorrectPassword =
                request.getPassword().equals(request.getConfirmPassword());
        if (!isCorrectPassword) {
            throw new IllegalStateException("Password is not confirmed!");
        }
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email is not valid!");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
