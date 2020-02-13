package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.general.*;

import java.util.List;

public interface ElectionDao {

    String giveVote(Voter voter, Candidate candidateToMayor);

    String giveVoteAgainstAll(Voter voter);

    int getMayor();

    void startElection();

    void stopElection();

    void newElection();

    Election electionCopy();

    List<Vote> voteCopy();

    List<AgainstAll> againstAllCopy();

    Election getElectionStatus();

    Vote getVote(Voter voter);

    Vote getAgainstAll(Voter voter);

    int getVotesCount(int mayorId);

    int getAgainstAllCount();
}
