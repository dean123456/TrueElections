package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class AddProposalToProgrammeDtoResponse implements Serializable {

    private static final long serialVersionUID = 21L;

    private String status;

    public AddProposalToProgrammeDtoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
