package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.Candidate;
import net.thumbtack.school.elections.general.CandidateList;
import net.thumbtack.school.elections.general.Programme;
import net.thumbtack.school.elections.general.Voter;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CandidateMapper {
    @Insert("INSERT INTO candidates (voter_id) VALUES (#{candidate.voter_id})")
    @Options(useGeneratedKeys = true, keyProperty = "candidate.id")
    void insert(@Param("candidate") Candidate candidate);

    @Insert("INSERT INTO candidates VALUES (#{candidate.id}, #{candidate.voter_id}, #{candidate.agree})")
    void insertAll(@Param("candidate") Candidate candidate);

    @Insert("UPDATE candidates SET agree = #{agree} WHERE id = #{candidate.id}")
    void setAgree(@Param("candidate") Candidate candidate, @Param("agree") boolean agree);

    @Select("SELECT id, voter_id, agree FROM candidates WHERE voter_id = #{voter.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "programme", column = "id", javaType = Programme.class,
                    one = @One(select = "net.thumbtack.school.elections.mappers.ProgrammeMapper.getProgramme", fetchType = FetchType.EAGER))
    })
    Candidate getCandidateByVoter(@Param("voter") Voter voter);

    @Select("SELECT id, voter_id, agree FROM candidates WHERE id = #{id}")
    Candidate getCandidateById(@Param("id") int id);

    @Delete("DELETE FROM candidates WHERE voter_id = #{voter.id}")
    void removeCandidate(@Param("voter") Voter voter);

    @Select("SELECT c.id, firstName, lastName, patronymic, agree FROM candidates as c INNER JOIN voters AS v ON voter_id = v.id WHERE agree = TRUE")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "programme", column = "id", javaType = Programme.class,
                    one = @One(select = "net.thumbtack.school.elections.mappers.ProgrammeMapper.getProgramme", fetchType = FetchType.EAGER))
    })
    List<CandidateList> getAllCandidates();

    @Select("SELECT id, voter_id, agree FROM candidates")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "programme", column = "id", javaType = Programme.class,
                    one = @One(select = "net.thumbtack.school.elections.mappers.ProgrammeMapper.getProgramme", fetchType = FetchType.EAGER))
    })
    List<Candidate> candidateCopy();
}
