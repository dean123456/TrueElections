package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class LogoutVoterDtoResponse implements Serializable {

    private static final long serialVersionUID = 37L;

    private String token;

    public LogoutVoterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
