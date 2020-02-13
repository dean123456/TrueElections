package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.ProposalDao;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ProposalDaoImpl extends DaoImpleBase implements ProposalDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalDaoImpl.class);

    @Override
    public String addProposal(Voter voter, Proposal proposal) throws ElectionException {
        LOGGER.debug("Add proposal {} to Database", proposal);
        try (SqlSession sqlSession = getSession()) {
            try {
                getProposalMapper(sqlSession).insert(voter, proposal);
                getRatingMapper(sqlSession).insert(voter, proposal);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add proposal {} to Database.", proposal, ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.DUPLICATE_PROPOSAL);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public List<ProposalAvgRating> getProposalAvgRatingList() {
        LOGGER.debug("Get proposal's list with average rating");
        try (SqlSession sqlSession = getSession()) {
            try {
                return Collections.unmodifiableList(getProposalMapper(sqlSession).getProposalListWithRating());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get proposal's list with average rating. ", ex);
                throw ex;
            }
        }
    }

    @Override
    public String addProposalRating(Voter voter, Proposal proposal, int rating) throws ElectionException {
        LOGGER.debug("Add rating {} to proposal {}", rating, proposal);
        try (SqlSession sqlSession = getSession()) {
            try {
                getRatingMapper(sqlSession).insertRating(voter, proposal.getId(), rating);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't Add rating {} to proposal {}. {}", rating, proposal, ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.CANT_EDIT_OWN_RATING);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public List<Voter> getProposalListByAllVoters() {
        LOGGER.debug("Get proposal's list by all voters");
        try (SqlSession sqlSession = getSession()) {
            try {
                return Collections.unmodifiableList(getVoterMapper(sqlSession).getVoterList());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get proposal's list by all voters. ", ex);
                throw ex;
            }
        }
    }

    @Override
    public Voter getProposalListByVoter(int voterId) {
        LOGGER.debug("Get voter with proposals");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVoterMapper(sqlSession).getVoterById(voterId);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get voter with proposals. ", ex);
                throw ex;
            }
        }
    }

    @Override
    public int removeProposalRating(Voter voter, Proposal proposal) {
        LOGGER.debug("Remove rating from proposal {} ", proposal);
        int rowsAffected;
        try (SqlSession sqlSession = getSession()) {
            try {
                rowsAffected = getRatingMapper(sqlSession).removeRating(proposal, voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't remove rating from proposal {}", proposal, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return rowsAffected;
    }

    @Override
    public Proposal getProposalByTitle(String proposalTitle) {
        LOGGER.debug("Get proposal by title {}", proposalTitle);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getProposalMapper(sqlSession).getProposalByTitle(proposalTitle);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get proposal. ", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Proposal> getProposalsByVoter(Voter voter) {
        LOGGER.debug("Get proposals by voter {}", voter);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getProposalMapper(sqlSession).getProposalsByVoter(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get proposals by voter. ", ex);
                throw ex;
            }
        }
    }

    @Override
    public int getVoterIdByProposal(Proposal proposal) {
        LOGGER.debug("Get voter id by proposal {}", proposal);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getProposalMapper(sqlSession).getVoterIdByProposal(proposal);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get voter id by proposal {}. ", proposal, ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Proposal> proposalCopy() {
        LOGGER.debug("Start proposal copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getProposalMapper(sqlSession).cityProposalCopy();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy voters.", ex);
                throw ex;
            }
        }
    }
}
