package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class OwnProgrammeDtoRequest implements Serializable {

    private static final long serialVersionUID = 25L;

    private String token;

    public OwnProgrammeDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public OwnProgrammeDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
