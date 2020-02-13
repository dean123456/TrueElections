package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class CandidateAgreeDtoRequest implements Serializable {

    private static final long serialVersionUID = 9L;

    private String token;
    private boolean agree;

    public CandidateAgreeDtoRequest(String token, boolean agree) {
        this.token = token;
        this.agree = agree;
    }

    public String getToken() {
        return token;
    }

    public boolean isAgree() {
        return agree;
    }

    public CandidateAgreeDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
