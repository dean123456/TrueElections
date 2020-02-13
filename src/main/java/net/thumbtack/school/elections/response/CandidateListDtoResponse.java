package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.CandidateList;

import java.io.Serializable;
import java.util.List;

public class CandidateListDtoResponse implements Serializable {

    private static final long serialVersionUID = 33L;

    private List<CandidateList> candidateList;

    public CandidateListDtoResponse(List<CandidateList> candidateList) {
        this.candidateList = candidateList;
    }

    public List<CandidateList> getCandidateList() {
        return candidateList;
    }
}
