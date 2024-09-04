package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import com.fixitnow.fix_it_now.Entity.UserEntity;
import com.fixitnow.fix_it_now.repository.ProjectRepository;
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
import static com.fixitnow.fix_it_now.util.Utils.getCurrentDate;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProjectEntity addProject(ProjectEntity projectEntity) {
        Objects.requireNonNull(projectEntity, BAD_REQUEST);
        if(!validateAddProjectRequest(projectEntity)){
            logger.warn(PROJECT_NAME_NULL_WR);
            throw new RuntimeException(BAD_REQUEST);
        }
        try{
            projectEntity.setCreatedDate(getCurrentDate());
            projectEntity.setUpdatedDate(getCurrentDate());
            projectEntity.setUserEntities(new ArrayList<>());
            projectEntity.setIssueEntities(new ArrayList<>());
            return projectRepository.save(projectEntity);
        } catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public ProjectEntity updateProject(Long id, ProjectEntity projectEntity) {
        Objects.requireNonNull(id, BAD_REQUEST);
        Objects.requireNonNull(projectEntity, BAD_REQUEST);
        try {
            Optional<ProjectEntity> existingProjectOptional = projectRepository.findById(id);
            if (existingProjectOptional.isPresent()) {
                ProjectEntity existingProjectEntity = existingProjectOptional.get();

                if(projectEntity.getName() != null){
                    existingProjectEntity.setName(projectEntity.getName());
                }
                if(projectEntity.getDescription() != null){
                    existingProjectEntity.setDescription(projectEntity.getDescription());
                }
                existingProjectEntity.setUpdatedDate(getCurrentDate());
                return projectRepository.save(existingProjectEntity);
            } else {
                logger.warn(PROJECT_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public ProjectEntity addUserToProject(Long projectId, Long userId){
        try{
            ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow(()->new RuntimeException(PROJECT_ID_NOT_FOUND));
            UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new RuntimeException(USER_ID_NOT_FOUND));
            projectEntity.getUserEntities().add(userEntity);
            return projectRepository.save(projectEntity);
        }catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public String deleteProject(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            if(projectRepository.existsById(id)){
                projectRepository.deleteById(id);
                return PROJECT_DELETE_SUCCESS +id;
            }else{
                logger.warn(PROJECT_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }


    public ProjectEntity getProject(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            Optional<ProjectEntity> project = projectRepository.findById(id);
            if(project.isPresent()){
                return project.get();
            }else{
                logger.warn(PROJECT_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND+PROJECT_ID_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    private boolean validateAddProjectRequest(ProjectEntity projectEntity) {
        return projectEntity.getName() != null;
    }
}
