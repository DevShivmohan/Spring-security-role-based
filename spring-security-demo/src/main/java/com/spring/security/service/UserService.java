package com.spring.security.service;

import com.spring.security.dao.UserRepository;
import com.spring.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * add a user with encrypted password
     * @param user
     * @return
     */
    public ResponseEntity<Object> addUser(User user){
        try{
            if(user!=null){
                List<User> users=userRepository.getAllUsers();
                if(users!=null || users.size()>0)
                    for(User user1:users)
                        if(user1.getUserName().equalsIgnoreCase(user.getUserName()))
                            return new ResponseEntity<>("Username "+user.getUserName()+" already exist in our database",HttpStatus.IM_USED);
                user.setEnabled(true);
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setCaptureDate(new Date());
                user=userRepository.save(user);
                if(user!=null)
                    return new ResponseEntity<>(user,HttpStatus.OK);
                else
                    return new ResponseEntity<>("Due to technical problem your request couldn't be processed",HttpStatus.I_AM_A_TEAPOT);
            }else
                return new ResponseEntity<>("Provide proper request body",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getting all registered users
     * @return
     */
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> users= userRepository.getAllUsers();
            if(users!=null && users.size()>0)
                return new ResponseEntity<List<User>>(users,HttpStatus.OK);
            else
                return new ResponseEntity("Could not found any user",HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * delete user by username
     * @param username
     * @return
     */
    public ResponseEntity<Object> deleteUser(String username){
        try{
            List<User> users=userRepository.getAllUsers();
            if(users!=null)
                for(User user:users)
                    if(user.getUserName().equalsIgnoreCase(username)){
                        userRepository.deleteById(user.getId());
                        return new ResponseEntity(user,HttpStatus.OK);
                    }
            return new ResponseEntity("User not found via this username "+username,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * update user via username
     * @param user
     * @param username
     * @return
     */
    public ResponseEntity<Object> updateUser(User user,String username){
        try{
            List<User> users=userRepository.getAllUsers();
            if(users!=null)
                for(User user1:users)
                    if(user1.getUserName().equalsIgnoreCase(username)){
                        user.setId(user1.getId());
                        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                        user.setCaptureDate(user1.getCaptureDate());
                        user= userRepository.save(user);
                        if(user!=null)
                            return new ResponseEntity(user,HttpStatus.OK);
                        else
                            return new ResponseEntity("Due to some technical problem user not updated",HttpStatus.GATEWAY_TIMEOUT);
                    }
            return new ResponseEntity("User not found via this username "+username,HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
