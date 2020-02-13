package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class AddCandidateDtoRequest implements Serializable {

    private static final long serialVersionUID = 3L;

    private int id;
    private String voterToken;

    public AddCandidateDtoRequest(String voterToken, int id) {
        this.id = id;
        this.voterToken = voterToken;
    }

    public int getId() {
        return id;
    }

    public String getVoterToken() {
        return voterToken;
    }

    public AddCandidateDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(voterToken, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
