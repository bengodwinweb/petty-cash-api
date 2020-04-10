package com.bengodwinweb.pettycash.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@PasswordMatches
public class UserSignupRequest {

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @Override
    public String toString() {
        return String.format("NewUser:\n\temail = %s\n\tnamae = %s %s\n\tpasswords match = %b", email, firstName, lastName, password.equals(matchingPassword));
    }
}
