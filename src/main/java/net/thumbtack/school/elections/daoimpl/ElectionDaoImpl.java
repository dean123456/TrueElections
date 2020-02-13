package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.ElectionDao;
import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ElectionDaoImpl extends DaoImpleBase implements ElectionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElectionDaoImpl.class);

    @Override
    public String giveVote(Voter voter, Candidate candidateToMayor) {
        LOGGER.debug("Give vote");
        try (SqlSession sqlSession = getSession()) {
            try {
                getElectionMapper(sqlSession).giveVote(voter, candidateToMayor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't give vote.", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public String giveVoteAgainstAll(Voter voter) {
        LOGGER.debug("Give vote");
        try (SqlSession sqlSession = getSession()) {
            try {
                getElectionMapper(sqlSession).giveVoteAgainstAll(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't give vote.", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public int getMayor() {
        LOGGER.debug("Get mayor");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getMayor();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get mayor.", ex);
                throw ex;
            }
        }
    }

    @Override
    public void startElection() {
        LOGGER.debug("Start Elections");
        try (SqlSession sqlSession = getSession()) {
            try {
                getElectionMapper(sqlSession).startElections();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't start Elections.", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void stopElection() {
        LOGGER.debug("Stop Elections");
        try (SqlSession sqlSession = getSession()) {
            try {
                getElectionMapper(sqlSession).stopElections();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't stop Elections.", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void newElection() {
        LOGGER.debug("Create new Elections");
        try (SqlSession sqlSession = getSession()) {
            try {
                getElectionMapper(sqlSession).newElection();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't create new Elections.", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public Election electionCopy() {
        LOGGER.debug("Start election copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getElectionStatus();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy election.", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<Vote> voteCopy() {
        LOGGER.debug("Start votes copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).voteCopy();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy votes.", ex);
                throw ex;
            }
        }
    }

    @Override
    public List<AgainstAll> againstAllCopy() {
        LOGGER.debug("Start against all copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).againstAllCopy();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy against all.", ex);
                throw ex;
            }
        }
    }

    @Override
    public Election getElectionStatus() {
        LOGGER.debug("Get election status");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getElectionStatus();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get election status.", ex);
                throw ex;
            }
        }
    }

    @Override
    public Vote getVote(Voter voter) {
        LOGGER.debug("Get vote by voter {}", voter);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getVote(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get vote by voter {}.", voter, ex);
                throw ex;
            }
        }
    }

    @Override
    public Vote getAgainstAll(Voter voter) {
        LOGGER.debug("Is voter {} against all?", voter);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getAgainstAll(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get vote by voter {}.", voter, ex);
                throw ex;
            }
        }
    }

    @Override
    public int getVotesCount(int mayorId) {
        LOGGER.debug("Get votes count by mayor id {}", mayorId);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getVotesCount(mayorId);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get votes count by mayor id {}", mayorId, ex);
                throw ex;
            }
        }
    }

    @Override
    public int getAgainstAllCount() {
        LOGGER.debug("Get against all count");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getElectionMapper(sqlSession).getAgainstAllCount();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get against all count", ex);
                throw ex;
            }
        }
    }
}
