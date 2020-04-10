package com.bengodwinweb.pettycash.dto.model;

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
// TODO - Make @PasswordMatches validator
public class NewUserDto {

    @NotNull
    @NotEmpty
    // TODO - Make @ValidEmail validator
    private String email;

    @NotNull
    @NotEmpty
    // TODO - Make @ValidPassword validator
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
