package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class RegisterVoterDtoResponse implements Serializable {

    private static final long serialVersionUID = 2L;

    private String token;

    public RegisterVoterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
