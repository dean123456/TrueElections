package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.Proposal;

import java.io.Serializable;
import java.util.List;

public class OwnProgrammeDtoResponse implements Serializable {

    private static final long serialVersionUID = 26L;

    private List<Proposal> programme;

    public OwnProgrammeDtoResponse(List<Proposal> programme) {
        this.programme = programme;
    }

    public List<Proposal> getProgramme() {
        return programme;
    }
}
