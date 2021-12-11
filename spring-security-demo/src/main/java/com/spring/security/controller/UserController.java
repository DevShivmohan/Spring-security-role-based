package com.spring.security.controller;

import com.spring.security.entity.User;
import com.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * show portal message
     * @return
     */
    @GetMapping(value = "/portal")
    public String disp(){
        return "Welcome in User portal";
    }

    /**
     * getting all users
     * @return
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * add a user
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Object> addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * update a user
     * @param user
     * @param username
     * @return
     */
    @RequestMapping(value = "/update/{username}",method = RequestMethod.PUT)
    public ResponseEntity<Object> addUser(@RequestBody User user, @PathVariable("username") String username){
        return userService.updateUser(user,username);
    }

    /**
     * delete a user
     * @param username
     * @return
     */
    @RequestMapping(value = "/delete/{username}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> addUser(@PathVariable("username") String username){
        return userService.deleteUser(username);
    }
}
