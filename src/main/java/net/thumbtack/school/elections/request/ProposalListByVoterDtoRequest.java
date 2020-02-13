package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class ProposalListByVoterDtoRequest implements Serializable {

    private static final long serialVersionUID = 15L;

    private int voterId;
    private String token;

    public ProposalListByVoterDtoRequest(String token, int voterId) {
        this.token = token;
        this.voterId = voterId;
    }

    public String getToken() {
        return token;
    }

    public int getVoterId() {
        return voterId;
    }

    public ProposalListByVoterDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
