package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class LogoutVoterDtoRequest implements Serializable {

    private static final long serialVersionUID = 5L;

    private String token;

    public LogoutVoterDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public LogoutVoterDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }

}
