package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class ElectionErrorDtoResponse implements Serializable {

    private static final long serialVersionUID = 47L;

    private String error;

    public ElectionErrorDtoResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
