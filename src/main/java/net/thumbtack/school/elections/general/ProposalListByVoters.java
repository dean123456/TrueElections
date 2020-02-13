package net.thumbtack.school.elections.general;

import java.util.List;
import java.util.Objects;

public class ProposalListByVoters {

    private String fullName;
    private List<String> proposalTitleList;

    public ProposalListByVoters(Voter voter, List<String> proposals) {
        fullName = voter.getFullName();
        proposalTitleList = proposals;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getProposalTitleList() {
        return proposalTitleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProposalListByVoters)) return false;
        ProposalListByVoters that = (ProposalListByVoters) o;
        return Objects.equals(getFullName(), that.getFullName()) &&
                Objects.equals(getProposalTitleList(), that.getProposalTitleList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getProposalTitleList());
    }
}
