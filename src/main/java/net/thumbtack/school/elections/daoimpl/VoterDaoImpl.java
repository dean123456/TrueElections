package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.Voter;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class VoterDaoImpl extends DaoImpleBase implements VoterDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoterDaoImpl.class);

    @Override
    public String insert(Voter voter) throws ElectionException {
        LOGGER.debug("Add voter {} to Database", voter);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoterMapper(sqlSession).insert(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add voter {} to Database. {}", voter, ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.DUPLICATE_LOGIN);
            }
            sqlSession.commit();
        }
        return voter.getToken();
    }

    @Override
    public String logout(Voter voter) {
        LOGGER.debug("Log out voter from Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).removeCandidate(voter);
                getRatingMapper(sqlSession).removeRatingWhenLogout(voter);
                getProposalMapper(sqlSession).makeByCity(voter);
                getVoterMapper(sqlSession).logout(voter);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't log out voter from Database", ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
        return null;
    }

    @Override
    public int login(String login, String password, String newToken) {
        LOGGER.debug("Sign in with login {} and password {}", login, password);
        int rowsAffected;
        try (SqlSession sqlSession = getSession()) {
            try {
                rowsAffected = getVoterMapper(sqlSession).login(login, password, newToken);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't sign in with login {} and password {}", login, password, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return rowsAffected;
    }

    @Override
    public List<Voter> getVoterList() throws ElectionException {
        LOGGER.debug("Get all voters");
        try (SqlSession sqlSession = getSession()) {
            try {
                return Collections.unmodifiableList(getVoterMapper(sqlSession).getVoterList());
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get all voters.", ex);
                throw new ElectionException(ElectionErrorCode.EMPTY_VOTER_LIST);
            }
        }
    }

    @Override
    public List<Voter> voterCopy() {
        LOGGER.debug("Start voter copy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVoterMapper(sqlSession).voterCopy();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't copy voters.", ex);
                throw ex;
            }
        }
    }

    @Override
    public Voter getVoterByToken(String token) throws ElectionException {
        LOGGER.debug("Get voter by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVoterMapper(sqlSession).getVoterByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get voter.", ex);
                throw new ElectionException(ElectionErrorCode.VOTER_NOT_FOUND);
            }
        }
    }

    @Override
    public Voter getVoterById(int voter_id) throws ElectionException {
        LOGGER.debug("Get voter by id {}", voter_id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVoterMapper(sqlSession).getVoterById(voter_id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get voter.", ex);
                throw new ElectionException(ElectionErrorCode.VOTER_NOT_FOUND);
            }
        }
    }
}
