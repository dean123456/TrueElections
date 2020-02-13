package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class RemoveProposalFromProgrammeDtoResponse implements Serializable {

    private static final long serialVersionUID = 24L;

    private String status;

    public RemoveProposalFromProgrammeDtoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
