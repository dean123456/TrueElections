package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class RemoveCandidateDtoRequest implements Serializable {

    private static final long serialVersionUID = 7L;

    private String token;

    public RemoveCandidateDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public RemoveCandidateDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
