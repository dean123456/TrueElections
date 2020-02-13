package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class GetMayorDtoRequest implements Serializable {

    private static final long serialVersionUID = 50L;

    private String token;

    public GetMayorDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public GetMayorDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
