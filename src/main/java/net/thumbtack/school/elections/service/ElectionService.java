package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.CandidateDaoImpl;
import net.thumbtack.school.elections.daoimpl.ElectionDaoImpl;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;
import net.thumbtack.school.elections.general.*;
import net.thumbtack.school.elections.request.GetMayorDtoRequest;
import net.thumbtack.school.elections.request.GiveVoteDtoRequest;
import net.thumbtack.school.elections.response.GetMayorDtoResponse;
import net.thumbtack.school.elections.response.GiveVoteDtoResponse;

public class ElectionService {

    public String giveVoteService(String jsonString) {
        GiveVoteDtoRequest giveVoteDtoRequest;
        try {
            giveVoteDtoRequest = new Gson().fromJson(jsonString, GiveVoteDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (!election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_NOT_STARTED_OR_FINISHED).getError()));
        GiveVoteDtoResponse giveVoteDtoResponse;
        String token = giveVoteDtoRequest.getToken();
        Voter voter;
        Vote vote;
        Vote againstAll;
        Candidate candidateToMayor;
        int id = giveVoteDtoRequest.getId();
        try {
            if (token == null) throw new ElectionException(ElectionErrorCode.NOT_LOGIN);
            voter = new VoterDaoImpl().getVoterByToken(token);
            vote = new ElectionDaoImpl().getVote(voter);
            againstAll = new ElectionDaoImpl().getAgainstAll(voter);
            if (vote != null || againstAll != null) throw new ElectionException(ElectionErrorCode.ALREADY_VOTED);
            if (id != -1) {
                candidateToMayor = new CandidateDaoImpl().getCandidateById(id);
                if (voter.getId() == candidateToMayor.getVoter_id())
                    throw new ElectionException(ElectionErrorCode.CANT_VOTE_TO_YOURSELF);
                giveVoteDtoResponse = new GiveVoteDtoResponse(new ElectionDaoImpl().giveVote(voter, candidateToMayor));
            } else {
                giveVoteDtoResponse = new GiveVoteDtoResponse(new ElectionDaoImpl().giveVoteAgainstAll(voter));
            }
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(giveVoteDtoResponse);
    }

    public String getMayorService(String jsonString) {
        GetMayorDtoRequest getMayorDtoRequest;
        try {
            getMayorDtoRequest = new Gson().fromJson(jsonString, GetMayorDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || !election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_NOT_FINISHED).getError()));
        GetMayorDtoResponse getMayorDtoResponse;
        int mayorId = new ElectionDaoImpl().getMayor();
        int votesCount = new ElectionDaoImpl().getVotesCount(mayorId);
        int againstAll = new ElectionDaoImpl().getAgainstAllCount();
        Candidate candidate;
        Voter voter;
        Mayor mayor;
        String electionResult;
        try {
            if (votesCount > againstAll) {
                candidate = new CandidateDaoImpl().getCandidateById(mayorId);
                voter = new VoterDaoImpl().getVoterById(candidate.getVoter_id());
                mayor = new Mayor(voter);
                electionResult = mayor.getFullName();
            } else electionResult = "Выборы не состоялись";
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        getMayorDtoResponse = new GetMayorDtoResponse(electionResult);
        return new Gson().toJson(getMayorDtoResponse);
    }
}
