package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.fixitnow.fix_it_now.util.Utils;

import static com.fixitnow.fix_it_now.constants.ResponseConstants.*;
import static com.fixitnow.fix_it_now.util.Utils.getCurrentDate;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project addProject(Project project) {
        Objects.requireNonNull(project, BAD_REQUEST);
        if(!validateAddProjectRequest(project)){
            logger.warn(PROJECT_NAME_NULL_WR);
            throw new RuntimeException(BAD_REQUEST);
        }
        try{
            project.setCreatedDate(getCurrentDate());
            project.setUpdatedDate(getCurrentDate());
            return projectRepository.save(project);
        } catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }


    @Transactional
    public Project updateProjectDetails(Long id, Project project) {
        Objects.requireNonNull(id, BAD_REQUEST);
        Objects.requireNonNull(project, BAD_REQUEST);
        try {
            Optional<Project> existingProjectOptional = projectRepository.findById(id);
            if (existingProjectOptional.isPresent()) {
                Project existingProject = existingProjectOptional.get();

                if(project.getId() != null){
                    existingProject.setId(project.getId());
                }
                if(project.getName() != null){
                    existingProject.setName(project.getName());
                }
                existingProject.setUpdatedDate(getCurrentDate());
                return projectRepository.save(existingProject);
            } else {
                logger.warn(PROJECT_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }


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


    public Project getProject(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            Optional<Project> project = projectRepository.findById(id);
            if(project.isPresent()){
                return project.get();
            }else{
                logger.warn(PROJECT_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    private boolean validateAddProjectRequest(Project project) {
        return project.getName() != null;
    }
}
