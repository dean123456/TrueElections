package net.thumbtack.school.elections.request;

import net.thumbtack.school.elections.exceptions.ElectionJsonParsingErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;

import java.io.Serializable;

public class GiveVoteDtoRequest implements Serializable {

    private static final long serialVersionUID = 29L;

    private String token;

    private int id;

    public GiveVoteDtoRequest(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public GiveVoteDtoRequest(String token) {
        this(token, -1);
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public GiveVoteDtoRequest validate() throws ElectionJsonParsingException {
        ElectionJsonParsingException.checkToken(token, ElectionJsonParsingErrorCode.WRONG_TOKEN);
        return this;
    }
}
