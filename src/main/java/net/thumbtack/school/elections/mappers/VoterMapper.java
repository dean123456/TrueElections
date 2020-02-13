package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.general.Voter;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface VoterMapper {
    @Insert("INSERT INTO voters(firstName, lastName, patronymic, street, house, apartment, login, voter_password, token) VALUES " +
            "(#{voter.firstName}, #{voter.lastName}, #{voter.patronymic}, #{voter.street}, #{voter.house}, #{voter.apartment}, " +
            "#{voter.login}, #{voter.password}, #{voter.token})")
    @Options(useGeneratedKeys = true, keyProperty = "voter.id")
    void insert(@Param("voter") Voter voter);

    @Select("SELECT id, firstName, lastName, patronymic FROM voters")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "proposalList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.ProposalMapper.getProposalsByVoter", fetchType = FetchType.EAGER))
    })
    List<Voter> getVoterList();

    @Select("SELECT id, firstName, lastName, patronymic FROM voters WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "proposalList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.ProposalMapper.getProposalsByVoter", fetchType = FetchType.EAGER))
    })
    Voter getVoterById(int id);

    @Select("SELECT * FROM voters WHERE token = #{token}")
    Voter getVoterByToken(String token);

    @Update("UPDATE voters SET token = #{token} WHERE login = #{login} AND voter_password = #{password}")
    int login(@Param("login") String login, @Param("password") String password, @Param("token") String token);

    @Delete("DELETE FROM voters")
    void deleteAll();

    @Update("UPDATE voters SET token = NULL WHERE token = #{voter.token}")
    void logout(@Param("voter") Voter voter);

    @Insert("INSERT INTO voters VALUES (#{voter.id}, #{voter.firstName}, #{voter.lastName}, #{voter.patronymic}, #{voter.street}, #{voter.house}, #{voter.apartment}, " +
            "#{voter.login}, #{voter.password}, #{voter.token})")
    void insertAll(@Param("voter") Voter voter);

    @Select("SELECT id, firstName, lastName, patronymic, street, house, apartment, login, voter_password as password, token FROM voters")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "proposalList", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.ProposalMapper.getProposalsByVoter", fetchType = FetchType.EAGER))
    })
    List<Voter> voterCopy();
}
