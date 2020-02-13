package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.Voter;

import java.io.Serializable;
import java.util.List;

public class ProposalListByAllVotersDtoResponse implements Serializable {

    private static final long serialVersionUID = 11L;

    private List<Voter> proposalListByVoters;

    public ProposalListByAllVotersDtoResponse(List<Voter> proposalListByVoters) {
        this.proposalListByVoters = proposalListByVoters;
    }

    public List<Voter> getProposalListByVoters() {
        return proposalListByVoters;
    }
}
