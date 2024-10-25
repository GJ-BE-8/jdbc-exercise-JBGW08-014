package com.nhnacademy.jdbc.student.repository;

import com.nhnacademy.jdbc.student.domain.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface StudentRepository {
    //todo#1 Connection connection parameter가 추가되었습니다.
    int save(Connection connection, Student student) throws SQLException;

    Optional<Student> findById(Connection connection, String id);

    int update(Connection connection, Student student);

    int deleteById(Connection connection, String id);

}