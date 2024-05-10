package com.ClientServerApp.Request;

import java.io.Serial;
import java.io.Serializable;

public record AuthorizationRequest(String name, String hashedPassword) implements Serializable {
    @Serial
    private static final long serialVersionUID = 2021L;

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "name='" + name + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}
