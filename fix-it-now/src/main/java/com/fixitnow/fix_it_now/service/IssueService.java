package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.model.Issue;
import com.fixitnow.fix_it_now.repository.IssueRepository;
import com.fixitnow.fix_it_now.repository.ProjectRepository;
import com.fixitnow.fix_it_now.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.fixitnow.fix_it_now.constants.IssueStatusConstants.*;
import static com.fixitnow.fix_it_now.constants.ResponseConstants.*;
import static com.fixitnow.fix_it_now.util.Utils.getCurrentDate;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

    @Autowired
    public IssueService(IssueRepository issueRepository, ProjectRepository projectRepository, UserRepository userRepository){
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Issue addIssue(Issue issue){
        Objects.requireNonNull(issue);
        if(!validateAddIssueRequest(issue)){
            logger.warn(ISSUE_TITLE_NULL_WR);
            throw new RuntimeException(BAD_REQUEST);
        }
        try{
            if(issue.getStatus() == null || issue.getStatus().isEmpty()){
                issue.setStatus(NEW);
            }
            issue.setCreatedDate(getCurrentDate());
            issue.setUpdatedDate(getCurrentDate());
            return issueRepository.save(issue);
        } catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    public Issue updateIssue(Long id, Issue issue){
        Objects.requireNonNull(id);
        Objects.requireNonNull(issue);
        try{
            Optional<Issue> existingIssueOptional = issueRepository.findById(id);
            if(existingIssueOptional.isPresent()){
                Issue existingIssue = existingIssueOptional.get();
                if(issue.getStatus() != null){
                    existingIssue.setStatus(issue.getStatus());
                }
                if(issue.getDescription() != null){
                    existingIssue.setDescription(issue.getDescription());
                }
                if(issue.getProject() != null){
                    existingIssue = updateIssueProject(issue, existingIssue);
                }
                if(issue.getTitle() != null){
                    existingIssue.setTitle(issue.getTitle());
                }
                if(issue.getAssignedUser() != null){
                    existingIssue = updateIssueAssignedUser(issue, existingIssue);
                }
                existingIssue.setUpdatedDate(getCurrentDate());
                return issueRepository.save(existingIssue);
            }else{
                logger.warn(ISSUE_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    public String deleteIssue(Long id){
        Objects.requireNonNull(id);
        try{
            if(issueRepository.existsById(id)){
                issueRepository.deleteById(id);
                return ISSUE_DELETE_SUCCESS +id;
            }else{
                logger.warn(ISSUE_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    public Issue getIssue(Long id) {
        Objects.requireNonNull(id, BAD_REQUEST);
        try{
            Optional<Issue> issue = issueRepository.findById(id);
            if(issue.isPresent()){
                return issue.get();
            }else{
                logger.warn(ISSUE_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    private Issue updateIssueAssignedUser(Issue issue, Issue existingIssue) {
        if(userRepository.existsById(issue.getAssignedUser().getId())){
            existingIssue.setAssignedUser(issue.getAssignedUser());
        }else {
            logger.warn(USER_ID_NOT_FOUND, issue.getAssignedUser().getId());
            throw new RuntimeException(NOT_FOUND);
        }
        return existingIssue;
    }

    private Issue updateIssueProject(Issue issue, Issue existingIssue) {
        if(projectRepository.existsById(issue.getProject().getId())){
            existingIssue.setProject(issue.getProject());
        }else {
            logger.warn(PROJECT_ID_NOT_FOUND, issue.getProject().getId());
            throw new RuntimeException(NOT_FOUND);
        }
        return existingIssue;
    }

    private boolean validateAddIssueRequest(Issue issue) {
        if(issue.getTitle() == null){
            return false;
        }
        return true;
    }
}
