package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        String sql = "Insert INTO jdbc_students (id, name, gender, age) values ('" + student.getId() + "', '" + student.getName() + "', '" + student.getGender() + "', " + student.getAge() + ")";
        try(
            Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()){
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.debug("error : student save");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(String id) throws SQLException {
        //todo#2 student 조회
        String sql = "SELECT id, name, gender, age FROM jdbc_students WHERE id = '" + id + "'";
        try(
            Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)
        ) {
            if (rs.next()) {
                // 데이터베이스에서 학생 정보를 가져와 Student 객체를 생성
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        Student.GENDER.valueOf(rs.getString("gender")),
                        rs.getInt("age")
                );
                return Optional.of(student);
            }
        }return Optional.empty();
    }

    @Override
    public int update(Student student) {
        String sql = String.format("update jdbc_students set name='%s', gender='%s', age=%d where id='%s' ",
                student.getName(),
                student.getGender(),
                student.getAge(),
                student.getId()
        );

        try (Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error updating student: {}", student.getId(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public int deleteById(String id){
        String sql = "DELETE FROM jdbc_students WHERE id = '" + id + "'";
        try (Connection conn = DbUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error while deleting student: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

}
