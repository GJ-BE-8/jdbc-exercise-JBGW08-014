package com.nhnacademy.jdbc.student.repository;

import com.nhnacademy.jdbc.student.domain.Student;

import java.sql.SQLException;
import java.util.Optional;

public interface StudentRepository {
    int save(Student student);

    Optional<Student> findById(String id) throws SQLException;

    int update(Student student);

    int deleteById(String id);
}