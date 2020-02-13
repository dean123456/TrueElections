package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.*;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;
import net.thumbtack.school.elections.general.*;
import net.thumbtack.school.elections.request.*;
import net.thumbtack.school.elections.response.*;

public class ProgrammeService {

    public String addProposalToProgrammeService(String jsonString) {
        AddProposalToProgrammeDtoRequest addProposalToProgrammeDtoRequest;
        try {
            addProposalToProgrammeDtoRequest = new Gson().fromJson(jsonString, AddProposalToProgrammeDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        AddProposalToProgrammeDtoResponse addProposalToProgrammeDtoResponse;
        Voter voter;
        Candidate candidate;
        Programme programme;
        Proposal proposal;
        try {
            voter = new VoterDaoImpl().getVoterByToken(addProposalToProgrammeDtoRequest.getToken());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (!candidate.isAgree())
                throw new ElectionException(ElectionErrorCode.DISAGREE);
            programme = new ProgrammeDaoImpl().getProgrammeByCandidate(candidate);
            proposal = new ProposalDaoImpl().getProposalByTitle(addProposalToProgrammeDtoRequest.getProposalTitle());
            addProposalToProgrammeDtoResponse = new AddProposalToProgrammeDtoResponse(new ProgrammeDaoImpl().addProposalToProgramme(proposal, programme));
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(addProposalToProgrammeDtoResponse);
    }

    public String removeProposalFromProgrammeService(String jsonString) {
        RemoveProposalFromProgrammeDtoRequest removeProposalFromProgrammeDtoRequest;
        try {
            removeProposalFromProgrammeDtoRequest = new Gson().fromJson(jsonString, RemoveProposalFromProgrammeDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        RemoveProposalFromProgrammeDtoResponse removeProposalFromProgrammeDtoResponse;
        Voter voter;
        Proposal proposal;
        Programme programme;
        try {
            voter = new VoterDaoImpl().getVoterByToken(removeProposalFromProgrammeDtoRequest.getToken());
            Candidate candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (!candidate.isAgree())
                return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.DISAGREE).getError()));
            proposal = new ProposalDaoImpl().getProposalByTitle(removeProposalFromProgrammeDtoRequest.getProposalTitle());
            programme = new ProgrammeDaoImpl().getProgrammeByCandidate(candidate);
            int proposelOwner_id = new ProposalDaoImpl().getVoterIdByProposal(proposal);
            if (proposelOwner_id == voter.getId()) {
                throw new ElectionException(ElectionErrorCode.CANT_REMOVE_OWN_PROPOSAL);
            }
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        removeProposalFromProgrammeDtoResponse = new RemoveProposalFromProgrammeDtoResponse(new ProgrammeDaoImpl().removeProposalFromProgramme(programme, proposal));
        return new Gson().toJson(removeProposalFromProgrammeDtoResponse);
    }

    public String getOwnProgrammeService(String jsonString) {
        OwnProgrammeDtoRequest ownProgrammeDtoRequest;
        try {
            ownProgrammeDtoRequest = new Gson().fromJson(jsonString, OwnProgrammeDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        OwnProgrammeDtoResponse ownProgrammeDtoResponse;
        Voter voter;
        Candidate candidate;
        try {
            voter = new VoterDaoImpl().getVoterByToken(ownProgrammeDtoRequest.getToken());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (!candidate.isAgree()) throw new ElectionException(ElectionErrorCode.DISAGREE);
            ownProgrammeDtoResponse = new OwnProgrammeDtoResponse(new ProgrammeDaoImpl().getOwnProgramme(candidate));
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(ownProgrammeDtoResponse);
    }
}
