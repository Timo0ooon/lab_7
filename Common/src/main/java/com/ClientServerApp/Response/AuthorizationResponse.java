package com.ClientServerApp.Response;

import java.io.Serial;
import java.io.Serializable;

public class AuthorizationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2023L;
    private boolean status;
    public AuthorizationResponse() {}

    public AuthorizationResponse(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AuthorizationResponse{" +
                "status=" + status +
                '}';
    }
}
