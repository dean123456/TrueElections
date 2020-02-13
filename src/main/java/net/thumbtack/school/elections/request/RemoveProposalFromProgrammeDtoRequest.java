package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class RemoveProposalFromProgrammeDtoRequest implements Serializable {

    private static final long serialVersionUID = 23L;

    private String token;
    private String proposalTitle;

    public RemoveProposalFromProgrammeDtoRequest(String token, String proposalTitle){
        this.token = token;
        this.proposalTitle = proposalTitle;
    }

    public String getToken() {
        return token;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public RemoveProposalFromProgrammeDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        ElectionJsonParsingException.checkProposal(proposalTitle, ElectionJsonParsingErrorCode.WRONG_PROPOSAL);
        return this;
    }
}
