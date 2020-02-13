package net.thumbtack.school.elections.general;

import java.io.Serializable;
import java.util.*;

public class Proposal implements Serializable {

    private static final long serialVersionUID = 52L;

    private int id;
    private String proposalTitle;
    private List<Rating> ratingList;

    private Proposal(int id, String proposalTitle, List<Rating> ratingList) {
        this.id = id;
        this.proposalTitle = proposalTitle;
        this.ratingList = ratingList;
    }

    private Proposal() {
    }

    public Proposal(String proposalTitle) {
        this(0, proposalTitle, new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposal)) return false;
        Proposal proposal = (Proposal) o;
        return getId() == proposal.getId() &&
                Objects.equals(getProposalTitle(), proposal.getProposalTitle()) &&
                Objects.equals(getRatingList(), proposal.getRatingList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProposalTitle(), getRatingList());
    }
}
