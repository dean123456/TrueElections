package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.Proposal;
import net.thumbtack.school.elections.general.Rating;
import net.thumbtack.school.elections.general.Voter;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RatingMapper {
    @Insert("INSERT INTO ratings (voter_id, proposal_id) VALUES (#{voter.id}, #{proposal.id})")
    @Options(useGeneratedKeys = true)
    void insert(@Param("voter") Voter voter, @Param("proposal") Proposal proposal);

    @Insert("INSERT INTO ratings (voter_id, rating, proposal_id) VALUES (#{voter.id}, #{rating}, #{proposal_id})")
    @Options(useGeneratedKeys = true)
    void insertRating(@Param("voter") Voter voter, @Param("proposal_id") int proposal_id, @Param("rating") int rating);

    @Delete("DELETE FROM ratings WHERE proposal_id = #{proposal.id} and voter_id = #{voter.id}")
    int removeRating(@Param("proposal") Proposal proposal, @Param("voter") Voter voter);

    @Delete("DELETE FROM ratings WHERE voter_id = #{voter.id}")
    void removeRatingWhenLogout(@Param("voter") Voter voter);

    @Select("SELECT id, voter_id, rating FROM ratings WHERE proposal_id = #{proposal.id}")
    List<Rating> getRatingsByProposal(@Param("proposal") Proposal proposal);

    @Insert("INSERT INTO ratings VALUES (#{rating.id}, #{rating.voter_id}, #{rating.rating}, #{proposal.id})")
    void insertAll(@Param("rating") Rating rating, @Param("proposal") Proposal proposal);
}
