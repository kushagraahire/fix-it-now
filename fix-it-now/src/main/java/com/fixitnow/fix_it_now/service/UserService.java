package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.Entity.UserEntity;
import com.fixitnow.fix_it_now.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public UserEntity addUser(UserEntity userEntity){
        Objects.requireNonNull(userEntity, BAD_REQUEST);
        if(!validateAddUserRequest(userEntity)){
            logger.warn(USER_REQUIRED_FIELDS);
            throw new RuntimeException(BAD_REQUEST);
        }
        try{
            userEntity.setCreatedDate(getCurrentDate());
            userEntity.setUpdatedDate(getCurrentDate());
            userEntity.setProjectEntities(new ArrayList<>());
            return userRepository.save(userEntity);
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public UserEntity updateUser(Long id, UserEntity userEntity){
        Objects.requireNonNull(id, BAD_REQUEST);
        Objects.requireNonNull(userEntity, BAD_REQUEST);
        try{
            Optional<UserEntity> existingUserOptional = userRepository.findById(id);
            if(existingUserOptional.isPresent()){
                UserEntity existingUserEntity = existingUserOptional.get();
                if(userEntity.getFirstName() != null){
                    existingUserEntity.setFirstName(userEntity.getFirstName());
                }
                if(userEntity.getLastName() != null){
                    existingUserEntity.setLastName(userEntity.getLastName());
                }
                if(userEntity.getEmailAddress() != null){
                    existingUserEntity.setEmailAddress(userEntity.getEmailAddress());
                }
                if(userEntity.getPassword() != null){
                    existingUserEntity.setPassword(userEntity.getPassword());
                }
                if(userEntity.getRole() != null){
                    existingUserEntity.setRole(userEntity.getRole());
                }
                if(userEntity.getStatus() != null){
                    existingUserEntity.setStatus(userEntity.getStatus());
                }
                if(userEntity.getProfilePicture() != null){
                    existingUserEntity.setProfilePicture(userEntity.getProfilePicture());
                }
                existingUserEntity.setUpdatedDate(getCurrentDate());
                return userRepository.save(existingUserEntity);
            }else{
                logger.warn(USER_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
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

    public UserEntity getProject(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            Optional<UserEntity> user = userRepository.findById(id);
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
    private boolean validateAddUserRequest(UserEntity userEntity) {
        return (userEntity.getFirstName() != null && userEntity.getLastName() != null && userEntity.getEmailAddress() != null && userEntity.getPassword() != null && userEntity.getRole() != null);
    }
}
