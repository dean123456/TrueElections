package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.*;

import java.util.List;

public interface CandidateDao {

    String insert(Candidate candidate, Programme programme) throws ElectionException;

    String insertAndAgree(Candidate candidate, Programme programme) throws ElectionException;

    String remove(Voter voter) throws ElectionException;

    String agree(Candidate candidate, Programme programme, boolean agree) throws ElectionException;

    List<CandidateList> getCandidateList();

    List<Candidate> candidateCopy();

    Candidate getCandidateByVoter(Voter voter) throws ElectionException;

    Candidate getCandidateById(int id) throws ElectionException;
}
