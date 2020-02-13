package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.Voter;

import java.io.Serializable;

public class ProposalListByVoterDtoResponse implements Serializable {

    private static final long serialVersionUID = 11L;

    Voter voterWithProposals;

    public ProposalListByVoterDtoResponse(Voter voterWithProposals) {
        this.voterWithProposals = voterWithProposals;
    }

    public Voter getVoterWithProposals() {
        return voterWithProposals;
    }
}
