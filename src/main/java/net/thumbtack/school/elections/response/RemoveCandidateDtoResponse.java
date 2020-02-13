package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class RemoveCandidateDtoResponse implements Serializable {

    private static final long serialVersionUID = 8L;

    private String status;

    public RemoveCandidateDtoResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
