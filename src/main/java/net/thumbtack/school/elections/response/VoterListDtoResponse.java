package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.Voter;

import java.io.Serializable;
import java.util.List;

public class VoterListDtoResponse implements Serializable {

    private static final long serialVersionUID = 32L;

    private List<Voter> voterList;

    public VoterListDtoResponse(List<Voter> voterList) {
        this.voterList = voterList;
    }

    public List<Voter> getVoterList() {
        return voterList;
    }
}
