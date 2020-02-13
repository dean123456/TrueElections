package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubjectMapper {

    @Delete("DELETE FROM subj")
    void deleteAll();

    @Delete("DELETE FROM subj WHERE id = #{subject.id};")
    void delete(@Param("subject") Subject subject);

    @Insert("INSERT INTO subj (subject_name) VALUES (#{subject.name})")
    @Options(useGeneratedKeys = true, keyProperty = "subject.id")
    Integer insert(@Param("subject") Subject subject);

    @Select("SELECT id, subject_name as name FROM subj WHERE id = #{id}")
    Subject getById(int id);

    @Select("SELECT id, subject_name as name FROM subj")
    @Results({
            @Result(property = "id", column = "id")
    })
    List<Subject> getAll();

    @Update("UPDATE subj SET subject_name = #{subject.name} WHERE id = #{subject.id}")
    Integer update(@Param("subject") Subject subject);

    @Select("SELECT id, subject_name AS name FROM subj WHERE id IN (SELECT subject_id FROM group_subject WHERE group_id = #{id})")
    @Results({
            @Result(property = "id", column = "id")
    })
    List<Subject> getByGroup(int id);
}
