package com.spring.security.dao;

import com.spring.security.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select u from User u where u.userName =:email")
    public User getUserByUserName(@Param("email") String email);

    @Query(value = "select * from user",nativeQuery = true)
    public List<User> getAllUsers();

}
