package com.nhnacademy.jdbc.user.repository;

import com.nhnacademy.jdbc.user.domain.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) throws SQLException;
    Optional<User> findById(String userId);
    int save(User user);
    int updateUserPasswordByUserId(String userId, String userPassword);
    int deleteByUserId(String userId);

}
