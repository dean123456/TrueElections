package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class ProposalListWithRatingDtoRequest implements Serializable {

    private static final long serialVersionUID = 16L;

    private String token;

    public ProposalListWithRatingDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public ProposalListWithRatingDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
