package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.Programme;
import net.thumbtack.school.elections.general.Proposal;
import net.thumbtack.school.elections.general.ProposalAvgRating;
import net.thumbtack.school.elections.general.Voter;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ProposalMapper {
    @Insert("INSERT INTO proposals (voter_id, proposalTitle) VALUES (#{voter.id}, #{proposal.proposalTitle})")
    @Options(useGeneratedKeys = true, keyProperty = "proposal.id")
    void insert(@Param("voter") Voter voter, @Param("proposal") Proposal proposal);

    @Select("SELECT proposalTitle, AVG(rating) AS avgRating FROM proposals AS p INNER JOIN " +
            "(SELECT rating, proposal_id FROM ratings) AS r ON p.id = r.proposal_id GROUP BY proposalTitle ORDER BY avgRating DESC")
    @Results({
            @Result(property = "proposalTitle", column = "proposalTitle"),
    })
    List<ProposalAvgRating> getProposalListWithRating();

    @Select("SELECT id, proposalTitle FROM proposals WHERE proposalTitle = #{proposalTitle}")
    Proposal getProposalByTitle(String proposalTitle);

    @Select("SELECT voter_id FROM proposals WHERE id = #{proposal.id}")
    int getVoterIdByProposal(@Param("proposal") Proposal proposal);

    @Select("SELECT id, proposalTitle FROM proposals WHERE voter_id = #{voter.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ratingList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.RatingMapper.getRatingsByProposal", fetchType = FetchType.EAGER))
    })
    List<Proposal> getProposalsByVoter(@Param("voter") Voter voter);

    @Update("UPDATE proposals SET voter_id = NULL WHERE voter_id = #{voter.id}")
    void makeByCity(@Param("voter") Voter voter);

    @Select("SELECT p.id, proposalTitle FROM proposals AS p INNER JOIN programme_proposals ON p.id = proposal_id WHERE programme_id = #{programme.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ratingList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.RatingMapper.getRatingsByProposal", fetchType = FetchType.EAGER))
    })
    List<Proposal> getProposalsByProgramme(@Param("programme") Programme programme);

    @Delete("DELETE FROM proposals")
    void deleteAll();

    @Insert("INSERT INTO proposals VALUES (#{proposal.id}, #{voter.id}, #{proposal.proposalTitle})")
    void insertAll(@Param("voter") Voter voter, @Param("proposal") Proposal proposal);

    @Insert("INSERT INTO proposals (id, proposalTitle) VALUES (#{proposal.id}, #{proposal.proposalTitle})")
    void insertAllCityProposals(@Param("proposal") Proposal proposal);

    @Select("SELECT id, proposalTitle FROM proposals WHERE voter_id IS NULL")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ratingList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.RatingMapper.getRatingsByProposal", fetchType = FetchType.EAGER))
    })
    List<Proposal> cityProposalCopy();
}
