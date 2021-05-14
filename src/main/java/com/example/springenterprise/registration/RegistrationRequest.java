package com.example.springenterprise.registration;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
