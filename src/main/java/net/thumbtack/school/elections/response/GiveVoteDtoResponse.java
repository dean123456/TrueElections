package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class GiveVoteDtoResponse implements Serializable {

    private static final long serialVersionUID = 30L;

    private String status;

    public GiveVoteDtoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
