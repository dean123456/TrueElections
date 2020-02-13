package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ElectionMapper {

    @Select("SELECT started, finished FROM elections")
    Election getElectionStatus();

    @Insert("INSERT INTO elections (started, finished) VALUES (FALSE, FALSE)")
    void newElection();

    @Update("UPDATE elections SET started = TRUE")
    void startElections();

    @Update("UPDATE elections SET started = false, finished = true")
    void stopElections();

    @Insert("INSERT INTO votes (voter_id, candidate_id) VALUES (#{voter.id}, #{candidate.id})")
    @Options(useGeneratedKeys = true)
    void giveVote(@Param("voter") Voter voter, @Param("candidate") Candidate candidate);

    @Insert("INSERT INTO against_all (voter_id) VALUES (#{voter.id})")
    @Options(useGeneratedKeys = true)
    void giveVoteAgainstAll(@Param("voter") Voter voter);

    @Insert("INSERT INTO votes VALUES (#{vote.id}, #{voter_id}, #{candidate_id})")
    void insertAll(@Param("vote") Vote vote, @Param("voter_id") int voter_id, @Param("candidate_id") int candidate_id);

    @Insert("INSERT INTO against_all (id, voter_id) VALUES (#{againstAll.id}, #{voter_id})")
    void insertAgainstAll(@Param("againstAll") AgainstAll againstAll, @Param("voter_id") int voter_id);

    @Select("SELECT candidate_id FROM votes GROUP BY candidate_id ORDER BY COUNT(*) DESC LIMIT 1")
    int getMayor();

    @Select("SELECT COUNT(*) FROM votes WHERE candidate_id = #{id} GROUP BY candidate_id")
    int getVotesCount(@Param("id") int id);

    @Select("SELECT COUNT(*) FROM against_all")
    int getAgainstAllCount();

    @Delete("DELETE FROM elections")
    void deleteAll();

    @Insert("INSERT INTO elections (started, finished) VALUES (#{election.started}, #{election.finished})")
    void insert(@Param("election") Election election);

    @Select("SELECT id, voter_id as voterId, candidate_id as candidateId FROM votes")
    @Results({
            @Result(property = "id", column = "id"),
    })
    List<Vote> voteCopy();

    @Select("SELECT * FROM votes WHERE voter_id = #{voter.id}")
    Vote getVote(@Param("voter") Voter voter);

    @Select("SELECT * FROM against_all WHERE voter_id = #{voter.id}")
    Vote getAgainstAll(@Param("voter") Voter voter);

    @Select("SELECT id, voter_id as voterId FROM against_all")
    @Results({
            @Result(property = "id", column = "id"),
    })
    List<AgainstAll> againstAllCopy();
}
