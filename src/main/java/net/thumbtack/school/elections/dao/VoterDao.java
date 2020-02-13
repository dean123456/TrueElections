package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.Voter;

import java.util.List;

public interface VoterDao {

    String insert(Voter voter) throws ElectionException;

    String logout(Voter voter);

    int login(String login, String password, String newToken);

    List<Voter> getVoterList() throws ElectionException;

    List<Voter> voterCopy();

    Voter getVoterByToken(String token) throws ElectionException;

    Voter getVoterById(int voter_id) throws ElectionException;
}
