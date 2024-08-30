package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.model.User;
import com.fixitnow.fix_it_now.repository.UserRepository;
import com.fixitnow.fix_it_now.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fixitnow.fix_it_now.constants.ResponseConstants.*;
import static com.fixitnow.fix_it_now.constants.ResponseConstants.INTERNAL_SERVER_ER;
import static com.fixitnow.fix_it_now.util.Utils.getCurrentDate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        Objects.requireNonNull(user, BAD_REQUEST);
        if(!validateAddUserRequest(user)){
            logger.warn(USER_REQUIRED_FIELDS);
            throw new RuntimeException(BAD_REQUEST);
        }
        try{
            user.setCreatedDate(getCurrentDate());
            user.setUpdatedDate(getCurrentDate());
            return userRepository.save(user);
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public User updateUserDetails(Long id, User user){
        Objects.requireNonNull(id, BAD_REQUEST);
        Objects.requireNonNull(user, BAD_REQUEST);
        try{
            Optional<User> existingUserOptional = userRepository.findById(id);
            if(existingUserOptional.isPresent()){
                User existingUser = existingUserOptional.get();
                if(user.getFirstName() != null){
                    existingUser.setFirstName(user.getFirstName());
                }
                if(user.getLastName() != null){
                    existingUser.setLastName(user.getLastName());
                }
                if(user.getEmailAddress() != null){
                    existingUser.setEmailAddress(user.getEmailAddress());
                }
                if(user.getPassword() != null){
                    existingUser.setPassword(user.getPassword());
                }
                if(user.getRole() != null){
                    existingUser.setRole(user.getRole());
                }
                if(user.getStatus() != null){
                    existingUser.setStatus(user.getStatus());
                }
                if(user.getProfilePicture() != null){
                    existingUser.setProfilePicture(user.getProfilePicture());
                }
                existingUser.setUpdatedDate(getCurrentDate());
                return userRepository.save(existingUser);
            }else{
                logger.warn(USER_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    public String deleteUser(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
                return USER_DELETE_SUCCESS +id;
            }else{
                logger.warn(USER_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    public User getProject(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                return user.get();
            }else{
                logger.warn(USER_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }
    private boolean validateAddUserRequest(User user) {
        return (user.getFirstName() != null && user.getLastName() != null && user.getEmailAddress() != null && user.getPassword() != null && user.getRole() != null);
    }
}
