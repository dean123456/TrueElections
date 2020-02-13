package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class AddProposalDtoResponse implements Serializable {

    private static final long serialVersionUID = 19L;

    private String status;

    private String notice;

    public AddProposalDtoResponse(String status, String notice) {
        this.status = status;
        this.notice = notice;
    }

    public String getStatus() {
        return status;
    }

    public String getNotice() {
        return notice;
    }
}
