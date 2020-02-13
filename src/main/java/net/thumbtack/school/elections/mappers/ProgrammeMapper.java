package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.Candidate;
import net.thumbtack.school.elections.general.Programme;
import net.thumbtack.school.elections.general.Proposal;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ProgrammeMapper {
    @Insert("INSERT INTO programmes (candidate_id) VALUES (#{candidate.id})")
    @Options(useGeneratedKeys = true, keyProperty = "programme.id")
    void insert(@Param("candidate") Candidate candidate, @Param("programme") Programme programme);

    @Insert("INSERT INTO programmes VALUES (#{programme.id}, #{candidate.id})")
    void insertAll(@Param("programme") Programme programme, @Param("candidate") Candidate candidate);

    @Select("SELECT id FROM programmes WHERE candidate_id = #{candidate.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "programmeProposalList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.ProposalMapper.getProposalsByProgramme", fetchType = FetchType.EAGER))
    })
    Programme getProgramme(@Param("candidate") Candidate candidate);

    @Insert("INSERT INTO programme_proposals (proposal_id, programme_id) VALUES (#{proposal.id}, #{programme.id})")
    @Options(useGeneratedKeys = true)
    void insertProposalToProgramme(@Param("proposal") Proposal proposal, @Param("programme") Programme programme);

    @Insert("INSERT INTO programme_proposals (proposal_id, programme_id) VALUES (#{proposal.id}, #{programme.id})")
    @Options(useGeneratedKeys = true)
    void insertAllProposalsToProgramme(@Param("proposal") Proposal proposal, @Param("programme") Programme programme);

    @Select("SELECT b.id, b.proposalTitle FROM programmes AS getNotice INNER JOIN " +
            "(SELECT programme_id, proposals.id, proposalTitle FROM programme_proposals INNER JOIN proposals ON proposal_id = proposals.id) AS b " +
            "ON getNotice.id = programme_id WHERE candidate_id = #{candidate.id}")
    @Results({
            @Result(property = "id", column = "id")
    })
    List<Proposal> getOwnProgramme(@Param("candidate") Candidate candidate);

    @Delete("DELETE FROM programme_proposals WHERE programme_id = #{programme.id} AND proposal_id = #{proposal.id}")
    void removeProposalFromProgramme(@Param("programme") Programme programme, @Param("proposal") Proposal proposal);
}
