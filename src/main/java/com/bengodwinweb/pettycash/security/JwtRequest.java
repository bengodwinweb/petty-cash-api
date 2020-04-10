package com.bengodwinweb.pettycash.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class JwtRequest implements Serializable {

    public static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;


}
