package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class VoterListDtoRequest implements Serializable {

    private static final long serialVersionUID = 31L;

    private String token;

    public VoterListDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public VoterListDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }

}
