package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.Candidate;
import net.thumbtack.school.elections.general.Programme;
import net.thumbtack.school.elections.general.Proposal;

import java.util.List;

public interface ProgrammeDao {

    String addProposalToProgramme(Proposal proposal, Programme programme) throws ElectionException;

    String removeProposalFromProgramme(Programme programme, Proposal proposal) throws ElectionException;

    List<Proposal> getOwnProgramme(Candidate candidate) throws ElectionException;

    Programme getProgrammeByCandidate(Candidate candidate);
}
