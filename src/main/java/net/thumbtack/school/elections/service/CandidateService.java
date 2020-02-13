package net.thumbtack.school.elections.service;

import com.google.gson.*;
import net.thumbtack.school.elections.daoimpl.*;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;
import net.thumbtack.school.elections.general.*;
import net.thumbtack.school.elections.request.AddCandidateDtoRequest;
import net.thumbtack.school.elections.request.CandidateAgreeDtoRequest;
import net.thumbtack.school.elections.request.CandidateListDtoRequest;
import net.thumbtack.school.elections.request.RemoveCandidateDtoRequest;
import net.thumbtack.school.elections.response.AddCandidateDtoResponse;
import net.thumbtack.school.elections.response.CandidateAgreeDtoResponse;
import net.thumbtack.school.elections.response.CandidateListDtoResponse;
import net.thumbtack.school.elections.response.RemoveCandidateDtoResponse;

public class CandidateService {

    public String addCandidateService(String jsonString) {
        AddCandidateDtoRequest addCandidateDtoRequest;
        try {
            addCandidateDtoRequest = new Gson().fromJson(jsonString, AddCandidateDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        AddCandidateDtoResponse addCandidateDtoResponse;
        Voter candidateVoter;
        Candidate newCandidate;
        Voter voter;
        Candidate candidate;
        String notice;
        try {
            candidateVoter = new VoterDaoImpl().getVoterById(addCandidateDtoRequest.getId());
            newCandidate = new Candidate(candidateVoter.getId());
            voter = new VoterDaoImpl().getVoterByToken(addCandidateDtoRequest.getVoterToken());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            if (candidate != null) {
                notice = candidate.getNotice();
            } else notice = "";
            if (voter.getId() == candidateVoter.getId()) {
                Programme programme = new Programme(new ProposalDaoImpl().getProposalsByVoter(candidateVoter));
                addCandidateDtoResponse = new AddCandidateDtoResponse(new CandidateDaoImpl().insertAndAgree(newCandidate, programme), notice);
            } else {
                Programme programme = new Programme();
                addCandidateDtoResponse = new AddCandidateDtoResponse(new CandidateDaoImpl().insert(newCandidate, programme), notice);
            }
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(addCandidateDtoResponse);
    }

    public String removeCandidateService(String jsonString) {
        RemoveCandidateDtoRequest removeCandidateDtoRequest;
        try {
            removeCandidateDtoRequest = new Gson().fromJson(jsonString, RemoveCandidateDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        RemoveCandidateDtoResponse removeCandidateDtoResponse;
        Voter voter;
        try {
            voter = new VoterDaoImpl().getVoterByToken(removeCandidateDtoRequest.getToken());
            removeCandidateDtoResponse = new RemoveCandidateDtoResponse(new CandidateDaoImpl().remove(voter));
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(removeCandidateDtoResponse);
    }

    public String agreeService(String jsonString) {
        CandidateAgreeDtoRequest candidateAgreeDtoRequest;
        try {
            candidateAgreeDtoRequest = new Gson().fromJson(jsonString, CandidateAgreeDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        CandidateAgreeDtoResponse candidateAgreeDtoResponse;
        Voter voter;
        Candidate candidate;
        Programme programme;
        try {
            voter = new VoterDaoImpl().getVoterByToken(candidateAgreeDtoRequest.getToken());
            candidate = new CandidateDaoImpl().getCandidateByVoter(voter);
            programme = new Programme(new ProposalDaoImpl().getProposalsByVoter(voter));
            candidateAgreeDtoResponse = new CandidateAgreeDtoResponse(new CandidateDaoImpl().agree(candidate, programme, candidateAgreeDtoRequest.isAgree()));
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(candidateAgreeDtoResponse);
    }

    public String getCandidateListService(String jsonString) {
        CandidateListDtoRequest candidateListDtoRequest;
        try {
            candidateListDtoRequest = new Gson().fromJson(jsonString, CandidateListDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        CandidateListDtoResponse candidateListDtoResponse = new CandidateListDtoResponse(new CandidateDaoImpl().getCandidateList());
        return new Gson().toJson(candidateListDtoResponse);
    }
}
