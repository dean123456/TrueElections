package net.thumbtack.school.elections.database;

import net.thumbtack.school.elections.general.*;

import java.io.Serializable;
import java.util.*;

public class DataBase implements Serializable {

    private static final long serialVersionUID = 40L;

    private List<Voter> voterCopy;
    private List<Proposal> cityProposalCopy;
    private List<Candidate> candidateCopy;
    private Election electionCopy;
    private List<Vote> voteCopy;
    private List<AgainstAll> againstAll;

    public DataBase(List<Voter> voterCopy, List<Proposal> cityProposalCopy, List<Candidate> candidateCopy, Election electionCopy, List<Vote> voteCopy, List<AgainstAll> againstAll) {
        this.voterCopy = voterCopy;
        this.cityProposalCopy = cityProposalCopy;
        this.candidateCopy = candidateCopy;
        this.electionCopy = electionCopy;
        this.voteCopy = voteCopy;
        this.againstAll = againstAll;
    }

    public List<Voter> getVoterCopy() {
        return voterCopy;
    }

    public List<Candidate> getCandidateCopy() {
        return candidateCopy;
    }

    public Election getElectionCopy() {
        return electionCopy;
    }

    public List<Vote> getVoteCopy() {
        return voteCopy;
    }

    public List<AgainstAll> getAgainstAll() {
        return againstAll;
    }

    public List<Proposal> getCityProposalCopy() {
        return cityProposalCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataBase)) return false;
        DataBase dataBase = (DataBase) o;
        return Objects.equals(getVoterCopy(), dataBase.getVoterCopy()) &&
                Objects.equals(getCityProposalCopy(), dataBase.getCityProposalCopy()) &&
                Objects.equals(getCandidateCopy(), dataBase.getCandidateCopy()) &&
                Objects.equals(getElectionCopy(), dataBase.getElectionCopy()) &&
                Objects.equals(getVoteCopy(), dataBase.getVoteCopy()) &&
                Objects.equals(getAgainstAll(), dataBase.getAgainstAll());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoterCopy(), getCityProposalCopy(), getCandidateCopy(), getElectionCopy(), getVoteCopy(), getAgainstAll());
    }
}
