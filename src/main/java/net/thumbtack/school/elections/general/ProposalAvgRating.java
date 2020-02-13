package net.thumbtack.school.elections.general;

import java.math.BigDecimal;
import java.util.Objects;

public class ProposalAvgRating {

    private String proposalTitle;
    private double avgRating;

    public ProposalAvgRating(String proposalTitle, BigDecimal avgRating) {
        this.proposalTitle = proposalTitle;
        this.avgRating = avgRating.doubleValue();
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProposalAvgRating)) return false;
        ProposalAvgRating that = (ProposalAvgRating) o;
        return Double.compare(that.getAvgRating(), getAvgRating()) == 0 &&
                Objects.equals(getProposalTitle(), that.getProposalTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProposalTitle(), getAvgRating());
    }
}
