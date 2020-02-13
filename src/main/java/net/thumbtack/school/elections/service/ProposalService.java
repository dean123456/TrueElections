package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.CandidateDaoImpl;
import net.thumbtack.school.elections.daoimpl.ElectionDaoImpl;
import net.thumbtack.school.elections.daoimpl.ProposalDaoImpl;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;
import net.thumbtack.school.elections.general.Candidate;
import net.thumbtack.school.elections.general.Election;
import net.thumbtack.school.elections.general.Proposal;
import net.thumbtack.school.elections.general.Voter;
import net.thumbtack.school.elections.request.*;
import net.thumbtack.school.elections.response.*;

public class ProposalService {

    public String addProposalService(String jsonString) {
        AddProposalDtoRequest addProposalDtoRequest;
        try {
            addProposalDtoRequest = new Gson().fromJson(jsonString, AddProposalDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        String token = addProposalDtoRequest.getToken();
        Proposal proposal = new Proposal(addProposalDtoRequest.getProposalTitle());
        AddProposalDtoResponse addProposalDtoResponse;
        Voter voter;
        Candidate candidate;
        String notice;
        try {
            voter = new VoterDaoImpl().getVoterByToken(token);
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (candidate != null) {
                notice = candidate.getNotice();
            } else notice = "";
            addProposalDtoResponse = new AddProposalDtoResponse(new ProposalDaoImpl().addProposal(voter, proposal), notice);
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(addProposalDtoResponse);
    }

    public String addProposalRatingService(String jsonString) {
        AddProposalRatingDtoRequest addProposalRatingDtoRequest;
        try {
            addProposalRatingDtoRequest = new Gson().fromJson(jsonString, AddProposalRatingDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        AddProposalRatingDtoResponse addProposalRatingDtoResponse;
        Voter voter;
        Proposal proposal;
        Candidate candidate;
        String notice;
        try {
            voter = new VoterDaoImpl().getVoterByToken(addProposalRatingDtoRequest.getToken());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (candidate != null) {
                notice = candidate.getNotice();
            } else notice = "";
            proposal = new ProposalDaoImpl().getProposalByTitle(addProposalRatingDtoRequest.getProposalTitle());
            addProposalRatingDtoResponse = new AddProposalRatingDtoResponse(new ProposalDaoImpl().addProposalRating(voter,
                    proposal, addProposalRatingDtoRequest.getRating()), notice);
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(addProposalRatingDtoResponse);
    }

    public String getProposalListByAllVotersService(String jsonString) {
        ProposalListByAllVotersDtoRequest proposalListByAllVotersDtoRequest;
        try {
            proposalListByAllVotersDtoRequest = new Gson().fromJson(jsonString, ProposalListByAllVotersDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        ProposalListByAllVotersDtoResponse proposalListByAllVotersDtoResponse;
        proposalListByAllVotersDtoResponse = new ProposalListByAllVotersDtoResponse(new ProposalDaoImpl().getProposalListByAllVoters());
        return new Gson().toJson(proposalListByAllVotersDtoResponse);
    }

    public String getProposalListByVoterService(String jsonString) {
        ProposalListByVoterDtoRequest proposalListByVoterDtoRequest;
        try {
            proposalListByVoterDtoRequest = new Gson().fromJson(jsonString, ProposalListByVoterDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        ProposalListByVoterDtoResponse proposalListByVoterDtoResponse = new ProposalListByVoterDtoResponse(new ProposalDaoImpl().getProposalListByVoter(proposalListByVoterDtoRequest.getVoterId()));
        return new Gson().toJson(proposalListByVoterDtoResponse);
    }

    public String getProposalListWithRatingService(String jsonString) {
        ProposalListWithRatingDtoRequest proposalListWithRatingDtoRequest;
        try {
            proposalListWithRatingDtoRequest = new Gson().fromJson(jsonString, ProposalListWithRatingDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        ProposalListWithRatingDtoResponse proposalListWithRatingDtoResponse = new ProposalListWithRatingDtoResponse(new ProposalDaoImpl().getProposalAvgRatingList());
        return new Gson().toJson(proposalListWithRatingDtoResponse);
    }

    public String removeProposalRatingService(String jsonString) {
        RemoveProposalRatingDtoRequest removeProposalRatingDtoRequest;
        try {
            removeProposalRatingDtoRequest = new Gson().fromJson(jsonString, RemoveProposalRatingDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        RemoveProposalRatingDtoResponse removeProposalRatingDtoResponse;
        Voter voter;
        Proposal proposal;
        Candidate candidate;
        String notice;
        try {
            voter = new VoterDaoImpl().getVoterByToken(removeProposalRatingDtoRequest.getToken());
            proposal = new ProposalDaoImpl().getProposalByTitle(removeProposalRatingDtoRequest.getProposalTitle());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            int proposalOwner_id = new ProposalDaoImpl().getVoterIdByProposal(proposal);
            if (proposalOwner_id != voter.getId()) {
                if (new ProposalDaoImpl().removeProposalRating(voter, proposal) == 0) {
                    throw new ElectionException(ElectionErrorCode.NO_RATING);
                }
            } else
                throw new ElectionException(ElectionErrorCode.CANT_REMOVE_OWN_RATING);
            if (candidate != null) {
                notice = candidate.getNotice();
            } else notice = "";
            removeProposalRatingDtoResponse = new RemoveProposalRatingDtoResponse("ok", notice);
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(removeProposalRatingDtoResponse);
    }

}
