package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class LoginVoterDtoResponse implements Serializable {

    private static final long serialVersionUID = 36L;

    private String token;

    public LoginVoterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
