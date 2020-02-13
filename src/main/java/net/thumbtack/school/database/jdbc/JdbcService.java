package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {

    public static void insertTrainee(Trainee trainee) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String insertTraineeQuery = "insert into trainee(first_name, last_name, rating) values(?, ?, ?)";
        try (PreparedStatement stmtInsertTrainee = conn.prepareStatement(insertTraineeQuery, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmtInsertTrainee.setString(1, trainee.getFirstName());
            stmtInsertTrainee.setString(2, trainee.getLastName());
            stmtInsertTrainee.setInt(3, trainee.getRating());
            stmtInsertTrainee.executeUpdate();
            ResultSet generatedKeys = stmtInsertTrainee.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idTrainee = generatedKeys.getInt(1);
                trainee.setId(idTrainee);
            } else {
                throw new SQLException("Creating Trainee faild, no ID obtained.");
            }
            conn.commit();
        }
    }

    public static void updateTrainee(Trainee trainee) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String updateTraineeQuery = "update trainee set first_name = ?, last_name = ?, rating = ? where id = " + trainee.getId();
        try (PreparedStatement stmtUpdateTrainee = conn.prepareStatement(updateTraineeQuery)) {
            stmtUpdateTrainee.setString(1, trainee.getFirstName());
            stmtUpdateTrainee.setString(2, trainee.getLastName());
            stmtUpdateTrainee.setInt(3, trainee.getRating());
            stmtUpdateTrainee.executeUpdate();
            conn.commit();
        }
    }

    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectTraineeByIdQuery = "SELECT * FROM trainee WHERE id = " + traineeId;
        try (PreparedStatement stmtSelectTraineeById = conn.prepareStatement(selectTraineeByIdQuery)) {
            ResultSet rs = stmtSelectTraineeById.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int rating = rs.getInt("rating");
                return new Trainee(id, firstName, lastName, rating);
            }
        }
        return null;
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectTraineeByIdQuery = "SELECT * FROM trainee WHERE id = " + traineeId;
        try (PreparedStatement stmtSelectTraineeById = conn.prepareStatement(selectTraineeByIdQuery)) {
            ResultSet rs = stmtSelectTraineeById.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                int rating = rs.getInt(5);
                return new Trainee(id, firstName, lastName, rating);
            }
        }
        return null;
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        Connection conn = JdbcUtils.getConnection();
        String selectTraineesQuery = "SELECT * FROM trainee";
        try (PreparedStatement stmtSelectTrainees = conn.prepareStatement(selectTraineesQuery)) {
            ResultSet rs = stmtSelectTrainees.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int rating = rs.getInt("rating");
                trainees.add(new Trainee(id, firstName, lastName, rating));
            }
            return trainees;
        }
    }

    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        List<Trainee> trainees = new ArrayList<>();
        Connection conn = JdbcUtils.getConnection();
        String selectTraineesQuery = "SELECT * FROM trainee";
        try (PreparedStatement stmtSelectTrainees = conn.prepareStatement(selectTraineesQuery)) {
            ResultSet rs = stmtSelectTrainees.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                int rating = rs.getInt(5);
                trainees.add(new Trainee(id, firstName, lastName, rating));
            }
            return trainees;
        }
    }

    public static void deleteTrainee(Trainee trainee) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String deleteTraineeQuery = "DELETE FROM trainee WHERE id = " + trainee.getId();
        try (PreparedStatement stmtDeleteTrainee = conn.prepareStatement(deleteTraineeQuery)) {
            stmtDeleteTrainee.executeUpdate();
        }
    }

    public static void deleteTrainees() throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String deleteTraineesQuery = "DELETE FROM trainee";
        try (PreparedStatement stmtDeleteTrainees = conn.prepareStatement(deleteTraineesQuery)) {
            stmtDeleteTrainees.executeUpdate();
        }
    }

    public static void insertSubject(Subject subject) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String insertSubjectQuery = "INSERT INTO subj(subject_name) VALUES(?)";
        try (PreparedStatement stmtInsertSubject = conn.prepareStatement(insertSubjectQuery, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmtInsertSubject.setString(1, subject.getName());
            stmtInsertSubject.executeUpdate();
            ResultSet generatedKeys = stmtInsertSubject.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idSubject = generatedKeys.getInt(1);
                subject.setId(idSubject);
            } else {
                throw new SQLException("Creating Subject faild, no ID obtained.");
            }
            conn.commit();
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectSubjectByIdQuery = "SELECT * FROM subj WHERE id = " + subjectId;
        try (PreparedStatement stmtSelectSubjectById = conn.prepareStatement(selectSubjectByIdQuery)) {
            ResultSet rs = stmtSelectSubjectById.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subject_name");
                return new Subject(id, name);
            }
        }
        return null;
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectSubjectByIdQuery = "SELECT * FROM subj WHERE id = " + subjectId;
        try (PreparedStatement stmtSelectSubjectById = conn.prepareStatement(selectSubjectByIdQuery)) {
            ResultSet rs = stmtSelectSubjectById.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                return new Subject(id, name);
            }
        }
        return null;
    }

    public static void deleteSubjects() throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String deleteSubjectsQuery = "DELETE FROM subj";
        try (PreparedStatement stmtDeleteSubjects = conn.prepareStatement(deleteSubjectsQuery)) {
            stmtDeleteSubjects.executeUpdate();
        }
    }

    public static void insertSchool(School school) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String insertSchoolQuery = "insert into school values(?, ?, ?)";
        try (PreparedStatement stmtInsertSchool = conn.prepareStatement(insertSchoolQuery, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmtInsertSchool.setNull(1, Types.INTEGER);
            stmtInsertSchool.setString(2, school.getName());
            stmtInsertSchool.setInt(3, school.getYear());
            stmtInsertSchool.executeUpdate();
            ResultSet generatedKeys = stmtInsertSchool.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idSchool = generatedKeys.getInt(1);
                school.setId(idSchool);
            } else {
                throw new SQLException("Creating School faild, no ID obtained.");
            }
            conn.commit();
        }
    }

    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectSchoolByIdQuery = "SELECT * FROM school WHERE id = " + schoolId;
        try (PreparedStatement stmtSelectSchoolById = conn.prepareStatement(selectSchoolByIdQuery)) {
            ResultSet rs = stmtSelectSchoolById.executeQuery();
            if (rs.next()) {
                int school_id = rs.getInt("id");
                String school_name = rs.getString("school_name");
                int school_year = rs.getInt("year_of_study");
                return new School(school_id, school_name, school_year);
            }
        }
        return null;
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String selectSchoolByIdQuery = "SELECT * FROM school WHERE school.id = " + schoolId;
        try (PreparedStatement stmtSelectSchoolById = conn.prepareStatement(selectSchoolByIdQuery)) {
            ResultSet rs = stmtSelectSchoolById.executeQuery();
            if (rs.next()) {
                int school_id = rs.getInt(1);
                String school_name = rs.getString(2);
                int school_year = rs.getInt(3);
                return new School(school_id, school_name, school_year);
            }
        }
        return null;
    }

    public static void deleteSchools() throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String deleteSchoolQuery = "DELETE FROM school";
        try (PreparedStatement stmtDeleteSchool = conn.prepareStatement(deleteSchoolQuery)) {
            stmtDeleteSchool.executeUpdate();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        String insertGroupQuery = "INSERT INTO school_group(school_id, group_name, room) VALUES(?, ?, ?)";
        try (PreparedStatement stmtInsertGroup = conn.prepareStatement(insertGroupQuery, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmtInsertGroup.setInt(1, school.getId());
            stmtInsertGroup.setString(2, group.getName());
            stmtInsertGroup.setString(3, group.getRoom());
            stmtInsertGroup.executeUpdate();
            ResultSet generatedKeys = stmtInsertGroup.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGroup = generatedKeys.getInt(1);
                group.setId(idGroup);
            } else {
                throw new SQLException("Creating School faild, no ID obtained.");
            }
            conn.commit();
        }
    }

    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        School school = null;
        Connection conn = JdbcUtils.getConnection();
        String selectSchoolByIdWithGroupsQuery = "SELECT * FROM school INNER JOIN school_group ON school.id = school_id WHERE school.id = " + id;
        try (PreparedStatement stmtSelectSchoolByIdWithGroups = conn.prepareStatement(selectSchoolByIdWithGroupsQuery)) {
            ResultSet rs = stmtSelectSchoolByIdWithGroups.executeQuery();
            while (rs.next()) {
                if (school == null) {
                    int school_id = rs.getInt(1);
                    String school_name = rs.getString(2);
                    int school_year = rs.getInt(3);
                    school = new School(school_id, school_name, school_year);
                }
                int group_id = rs.getInt(4);
                String group_name = rs.getString(6);
                String group_room = rs.getString(7);
                school.getGroups().add(new Group(group_id, group_name, group_room));
            }
        }
        return school;
    }

    public static List<School> getSchoolsWithGroups() throws SQLException {
        List<School> schools = new ArrayList<>();
        Connection conn = JdbcUtils.getConnection();
        String selectSchoolsWithGroupsQuery = "SELECT * FROM school INNER JOIN school_group ON school.id = school_id";
        try (PreparedStatement stmtSelectSchoolWithGroups = conn.prepareStatement(selectSchoolsWithGroupsQuery)) {
            ResultSet rs = stmtSelectSchoolWithGroups.executeQuery();
            int id = 0;
            while (rs.next()) {
                int school_id = rs.getInt(1);
                String school_name = rs.getString(2);
                int school_year = rs.getInt(3);
                int group_id = rs.getInt(4);
                String group_name = rs.getString(6);
                String group_room = rs.getString(7);
                Group group = new Group(group_id, group_name, group_room);
                if (id != school_id) {
                    id = school_id;
                    School school = new School(school_id, school_name, school_year);
                    school.addGroup(group);
                    schools.add(school);
                } else {
                    for (School s : schools) {
                        if (s.getId() == school_id) {
                            s.addGroup(group);
                        }
                    }
                }
            }
            return schools;
        }
    }
}
