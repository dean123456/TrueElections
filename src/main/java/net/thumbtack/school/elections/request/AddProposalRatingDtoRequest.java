package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class AddProposalRatingDtoRequest implements Serializable {

    private static final long serialVersionUID = 17L;

    private String token;
    private String proposalTitle;
    private int rating;

    public AddProposalRatingDtoRequest(String token, String proposalTitle, int rating) {
        this.token = token;
        this.proposalTitle = proposalTitle;
        this.rating = rating;
    }

    public String getToken() {
        return token;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public int getRating() {
        return rating;
    }

    public AddProposalRatingDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        ElectionJsonParsingException.checkProposal(proposalTitle, ElectionJsonParsingErrorCode.WRONG_PROPOSAL);
        ElectionJsonParsingException.checkRating(rating, ElectionJsonParsingErrorCode.WRONG_RATING);
        return this;
    }
}
