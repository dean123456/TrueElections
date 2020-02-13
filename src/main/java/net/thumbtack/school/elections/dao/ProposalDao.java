package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.*;

import java.util.List;

public interface ProposalDao {

    String addProposal(Voter voter, Proposal proposal) throws ElectionException;

    List<ProposalAvgRating> getProposalAvgRatingList() throws ElectionException;

    String addProposalRating(Voter voter, Proposal proposal, int rating) throws ElectionException;

    List<Voter> getProposalListByAllVoters();

    Voter getProposalListByVoter(int voterId);

    int removeProposalRating(Voter voter, Proposal proposal);

    Proposal getProposalByTitle(String proposalTitle);

    List<Proposal> getProposalsByVoter(Voter voter);

    int getVoterIdByProposal(Proposal proposal);

    List<Proposal> proposalCopy();
}
