package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.ProgrammeDao;
import net.thumbtack.school.elections.exceptions.ElectionErrorCode;
import net.thumbtack.school.elections.exceptions.ElectionException;
import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class ProgrammeDaoImpl extends DaoImpleBase implements ProgrammeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgrammeDaoImpl.class);

    @Override
    public String addProposalToProgramme(Proposal proposal, Programme programme) throws ElectionException {
        LOGGER.debug("Add proposal {} to programme {}", proposal, programme);
        try (SqlSession sqlSession = getSession()) {
            try {
                getProgrammeMapper(sqlSession).insertProposalToProgramme(proposal, programme);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't add proposal {} to programme {}.", proposal, programme, ex);
                sqlSession.rollback();
                throw new ElectionException(ElectionErrorCode.DUPLICATE_PROPOSAL_IN_PROGRAMME);
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public String removeProposalFromProgramme(Programme programme, Proposal proposal) {
        LOGGER.debug("Remove proposal {} from programme {}", proposal, programme);
        try (SqlSession sqlSession = getSession()) {
            try {
                getProgrammeMapper(sqlSession).removeProposalFromProgramme(programme, proposal);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't remove proposal {} from programme {}.", proposal, programme, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return "ok";
    }

    @Override
    public List<Proposal> getOwnProgramme(Candidate candidate) {
        LOGGER.debug("Get own programme");
        try (SqlSession sqlSession = getSession()) {
            try {
                return Collections.unmodifiableList(getProgrammeMapper(sqlSession).getOwnProgramme(candidate));
            } catch (RuntimeException ex) {
                LOGGER.info("Can't Get own programme.", ex);
                throw ex;
            }
        }
    }

    @Override
    public Programme getProgrammeByCandidate(Candidate candidate) {
        LOGGER.debug("Get {} programme", candidate);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getProgrammeMapper(sqlSession).getProgramme(candidate);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get programme.", ex);
                throw ex;
            }
        }
    }
}
