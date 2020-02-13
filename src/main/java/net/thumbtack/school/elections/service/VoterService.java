package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.ElectionDaoImpl;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionErrors;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.exceptions.ElectionJsonParsingException;
import net.thumbtack.school.elections.general.Election;
import net.thumbtack.school.elections.general.Voter;
import net.thumbtack.school.elections.request.*;
import net.thumbtack.school.elections.response.*;

import java.util.UUID;

public class VoterService {

    public String registerService(String jsonString) {
        RegisterVoterDtoRequest registerVoterDtoRequest;
        try {
            registerVoterDtoRequest = new Gson().fromJson(jsonString, RegisterVoterDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        RegisterVoterDtoResponse registerVoterDtoResponse;
        Voter voter = new Voter(registerVoterDtoRequest.getFirstName(), registerVoterDtoRequest.getLastName(),
                registerVoterDtoRequest.getPatronymic(), registerVoterDtoRequest.getStreet(),
                registerVoterDtoRequest.getHouse(), registerVoterDtoRequest.getApartment(),
                registerVoterDtoRequest.getLogin(), registerVoterDtoRequest.getPassword());

        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        try {
            registerVoterDtoResponse = new RegisterVoterDtoResponse(new VoterDaoImpl().insert(voter));
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(registerVoterDtoResponse);
    }

    public String logoutService(String jsonString) {
        LogoutVoterDtoRequest logoutVoterDtoRequest;
        try {
            logoutVoterDtoRequest = new Gson().fromJson(jsonString, LogoutVoterDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Voter voter;
        try {
            voter = new VoterDaoImpl().getVoterByToken(logoutVoterDtoRequest.getToken());
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        LogoutVoterDtoResponse logoutVoterDtoResponse = new LogoutVoterDtoResponse(new VoterDaoImpl().logout(voter));
        return new Gson().toJson(logoutVoterDtoResponse);
    }

    public String loginService(String jsonString) {
        LoginVoterDtoRequest loginVoterDtoRequest;
        try {
            loginVoterDtoRequest = new Gson().fromJson(jsonString, LoginVoterDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        Election election = new ElectionDaoImpl().getElectionStatus();
        if (election.isStarted() || election.isFinished())
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.ELECTIONS_STARTED_OR_FINISHED).getError()));
        String newToken = UUID.randomUUID().toString();
        if (new VoterDaoImpl().login(loginVoterDtoRequest.getLogin(), loginVoterDtoRequest.getPassword(), newToken) == 0) {
            return new Gson().toJson(new ElectionErrors(new ElectionException(ElectionErrorCode.WRONG_LOGIN_OR_PASSWORD).getError()));
        }
        LoginVoterDtoResponse loginVoterDtoResponse = new LoginVoterDtoResponse(newToken);
        return new Gson().toJson(loginVoterDtoResponse);
    }

    public String voterListService(String jsonString) {
        VoterListDtoRequest voterListDtoRequest;
        try {
            voterListDtoRequest = new Gson().fromJson(jsonString, VoterListDtoRequest.class).validate();
        } catch (ElectionJsonParsingException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        VoterListDtoResponse voterListDtoResponse;
        try {
            voterListDtoResponse = new VoterListDtoResponse(new VoterDaoImpl().getVoterList());
        } catch (ElectionException e) {
            return new Gson().toJson(new ElectionErrors(e.getError()));
        }
        return new Gson().toJson(voterListDtoResponse);
    }
}
