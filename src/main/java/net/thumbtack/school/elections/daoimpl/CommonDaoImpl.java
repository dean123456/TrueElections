package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.CommonDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends DaoImpleBase implements CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    @Override
    public void clear() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoterMapper(sqlSession).deleteAll();
                getProposalMapper(sqlSession).deleteAll();
                getElectionMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void insertAll(DataBase database) {
        LOGGER.debug("Insert all to Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                for (Voter voter : database.getVoterCopy()) {
                    getVoterMapper(sqlSession).insertAll(voter);
                }
                for (Voter voter : database.getVoterCopy()) {
                    for (Proposal proposal : voter.getProposalList()) {
                        getProposalMapper(sqlSession).insertAll(voter, proposal);
                        for (Rating rating : proposal.getRatingList()) {
                            getRatingMapper(sqlSession).insertAll(rating, proposal);
                        }
                    }
                }
                for (Proposal proposal : database.getCityProposalCopy()) {
                    getProposalMapper(sqlSession).insertAllCityProposals(proposal);
                    for (Rating rating : proposal.getRatingList()) {
                        getRatingMapper(sqlSession).insertAll(rating, proposal);
                    }
                }
                for (Candidate candidate : database.getCandidateCopy()) {
                    getCandidateMapper(sqlSession).insertAll(candidate);
                    getProgrammeMapper(sqlSession).insertAll(candidate.getProgramme(), candidate);
                    for (Proposal proposal : candidate.getProgramme().getProgrammeProposalList()) {
                        getProgrammeMapper(sqlSession).insertAllProposalsToProgramme(proposal, candidate.getProgramme());
                    }
                }
                getElectionMapper(sqlSession).insert(database.getElectionCopy());
                for (Vote vote : database.getVoteCopy()) {
                    getElectionMapper(sqlSession).insertAll(vote, vote.getVoterId(), vote.getCandidateId());
                }
                for (AgainstAll againstAll : database.getAgainstAll()) {
                    getElectionMapper(sqlSession).insertAgainstAll(againstAll, againstAll.getVoterId());
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert all to database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
