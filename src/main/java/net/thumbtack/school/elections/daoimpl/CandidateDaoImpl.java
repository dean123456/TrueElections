package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CandidateDaoImpl extends DaoImpleBase implements CandidateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateDaoImpl.class);

    @Override
    public String insert(Candidate candidate, Programme programme) throws ElectionException {
        LOGGER.debug("Add candidate to Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).insert(candidate);
                getProgrammeMapper(sqlSession).insert(candidate, programme);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add candidate to Database.", ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.DUPLICATE_CANDIDATE);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public String insertAndAgree(Candidate candidate, Programme programme) throws ElectionException {
        LOGGER.debug("Add candidate to Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).insert(candidate);
                getProgrammeMapper(sqlSession).insert(candidate, programme);
                getCandidateMapper(sqlSession).setAgree(candidate, true);
                for (Proposal proposal : programme.getProgrammeProposalList()) {
                    getProgrammeMapper(sqlSession).insertProposalToProgramme(proposal, programme);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add candidate to Database.", ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.DUPLICATE_CANDIDATE);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public String remove(Voter voter) throws ElectionException {
        LOGGER.debug("Remove candidate from Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).removeCandidate(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't remove candidate from Database.", ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.CANDIDATE_NOT_FOUND);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public String agree(Candidate candidate, Programme programme, boolean agree) {
        LOGGER.debug("Add decision {} to candidate {}", agree, candidate);
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).setAgree(candidate, agree);
                for (Proposal proposal : programme.getProgrammeProposalList()) {
                    getProgrammeMapper(sqlSession).insertProposalToProgramme(proposal, candidate.getProgramme());
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add decision {} to candidate {}.", agree, candidate, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public List<CandidateList> getCandidateList() {
        LOGGER.debug("Get candidate list with progrmmes from Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                return Collections.unmodifiableList(getCandidateMapper(sqlSession).getAllCandidates());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get candidate list with progrmmes from Database.", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Candidate> candidateCopy() {
        LOGGER.debug("Start candidate copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getCandidateMapper(sqlSession).candidateCopy();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy candidates.", ex);
                throw ex;
            }
        }
    }

    @Override
    public Candidate getCandidateByVoter(Voter voter) throws ElectionException {
        LOGGER.debug("Get candidate by voter {}", voter);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getCandidateMapper(sqlSession).getCandidateByVoter(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get candidate.", ex);
                throw new ElectionException(ElectionErrorCode.CANDIDATE_NOT_FOUND);
            }
        }
    }

    @Override
    public Candidate getCandidateById(int id) throws ElectionException {
        LOGGER.debug("Get candidate by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getCandidateMapper(sqlSession).getCandidateById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get candidate by id {}.", id, ex);
                throw new ElectionException(ElectionErrorCode.CANDIDATE_NOT_FOUND);
            }
        }
    }
}
