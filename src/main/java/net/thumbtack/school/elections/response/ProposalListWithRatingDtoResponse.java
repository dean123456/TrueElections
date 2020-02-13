package net.thumbtack.school.elections.response;

import net.thumbtack.school.elections.general.Proposal;
import net.thumbtack.school.elections.general.ProposalAvgRating;

import java.io.Serializable;
import java.util.List;

public class ProposalListWithRatingDtoResponse implements Serializable {

    private static final long serialVersionUID = 13L;

    private List<ProposalAvgRating> proposalListWithRating;

    public ProposalListWithRatingDtoResponse(List<ProposalAvgRating> proposalListWithRating) {
        this.proposalListWithRating = proposalListWithRating;
    }

    public List<ProposalAvgRating> getProposalListWithRating() {
        return proposalListWithRating;
    }
}
