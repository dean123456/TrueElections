package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class ProposalListByAllVotersDtoRequest implements Serializable {

    private static final long serialVersionUID = 14L;

    private String token;

    public ProposalListByAllVotersDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public ProposalListByAllVotersDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
