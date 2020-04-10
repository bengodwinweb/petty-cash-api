package com.bengodwinweb.pettycash.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "role")
public class Role {
    @Id
    private String id;

    private String role;

    @Override
    public String toString() {
        return "Role:\n\tid: " + id + "\n\trole: " + role;
    }
}
