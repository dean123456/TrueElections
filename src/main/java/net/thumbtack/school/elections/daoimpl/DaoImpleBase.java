package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.mappers.*;
import net.thumbtack.school.elections.utils.ElectionsUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImpleBase {
    protected SqlSession getSession(){
        return ElectionsUtils.getSqlSessionFactory().openSession();
    }

    protected VoterMapper getVoterMapper(SqlSession sqlSession){
        return sqlSession.getMapper(VoterMapper.class);
    }

    protected CandidateMapper getCandidateMapper(SqlSession sqlSession){
        return sqlSession.getMapper(CandidateMapper.class);
    }

    protected ElectionMapper getElectionMapper(SqlSession sqlSession){
        return sqlSession.getMapper(ElectionMapper.class);
    }

    protected ProgrammeMapper getProgrammeMapper(SqlSession sqlSession){
        return sqlSession.getMapper(ProgrammeMapper.class);
    }

    protected ProposalMapper getProposalMapper(SqlSession sqlSession){
        return sqlSession.getMapper(ProposalMapper.class);
    }

    protected RatingMapper getRatingMapper(SqlSession sqlSession){
        return sqlSession.getMapper(RatingMapper.class);
    }
}
