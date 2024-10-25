package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student) throws SQLException {
        //todo#2 학생등록
        String sql = "insert into jdbc_students(id,name,gender,age) values(?,?,?,?)";
  ;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3,student.getGender().name());
            preparedStatement.setInt(4,student.getAge());
            return preparedStatement.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        String sql = "select * from jdbc_students where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Student student = new Student(
                rs.getString("id"),
                rs.getString("name"),
                Student.GENDER.valueOf(rs.getString("gender")),
                rs.getInt("age"));
                return Optional.of(student);
            }
         else{
             return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        String sql ="update jdbc_students set name= ?, gender =?, age =? where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(4, student.getId());
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2,student.getGender().name());
            preparedStatement.setInt(3,student.getAge());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        String sql = " delete from jdbc_students where id =?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}