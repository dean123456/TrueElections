package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class CandidateAgreeDtoResponse implements Serializable {

    private static final long serialVersionUID = 9L;

    private String status;

    public CandidateAgreeDtoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
