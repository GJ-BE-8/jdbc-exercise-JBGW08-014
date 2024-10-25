package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 학생 등록
        String sql = "insert INTO jdbc_students (id, name, gender, age) values (?,?,?,?)";
        try(
            Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql)) {
            preparedStatement.setString(1,student.getId());
            preparedStatement.setString(2,student.getName());
            preparedStatement.setString(3, student.getGender().name());
            preparedStatement.setInt(4,student.getAge());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Student> findById(String id){
        //todo#2 학생 조회
        String sql = "SELECT * FROM jdbc_students WHERE id = ?";
        try(
            Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        Student.GENDER.valueOf(rs.getString("gender")),
                        rs.getInt("age")
                );
                return Optional.of(student);
            }else{
                return Optional.empty();
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Student student){
        //todo#3 학생 수정 , name 수정
        String sql =  "UPDATE jdbc_students SET name = ?, gender = ? ,age = ? WHERE id = ?";
        try(
            Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getGender().name());
            preparedStatement.setInt(3,student.getAge());
            preparedStatement.setString(4, student.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id){
        //todo#4 학생 삭제
        String sql = "DELETE FROM jdbc_students WHERE id = ?";
        try(
            Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
