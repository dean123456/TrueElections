package net.thumbtack.school.elections.response;

import java.io.Serializable;

public class GetMayorDtoResponse implements Serializable {

    private static final long serialVersionUID = 51L;

    private String mayor;

    public GetMayorDtoResponse(String mayor) {
        this.mayor = mayor;
    }

    public String getMayor() {
        return mayor;
    }
}
