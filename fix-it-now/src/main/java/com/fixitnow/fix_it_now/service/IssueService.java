package com.fixitnow.fix_it_now.service;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.Mapper.IssueMapper;
import com.fixitnow.fix_it_now.model.Issue;
import com.fixitnow.fix_it_now.repository.IssueRepository;
import com.fixitnow.fix_it_now.repository.ProjectRepository;
import com.fixitnow.fix_it_now.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.fixitnow.fix_it_now.Mapper.IssueMapper.toIssue;
import static com.fixitnow.fix_it_now.Mapper.IssueMapper.toIssueEntity;
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

    @Transactional
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
            IssueEntity issueEntity = toIssueEntity(issue);
            issueEntity = issueRepository.save(issueEntity);
            return toIssue(issueEntity);
        } catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
    public Issue updateIssue(Long id, Issue issue){
        Objects.requireNonNull(id);
        Objects.requireNonNull(issue);
        try{
            Optional<IssueEntity> existingIssueOptional = issueRepository.findById(id);
            if(existingIssueOptional.isPresent()){
                IssueEntity existingIssueEntity = existingIssueOptional.get();
                if(issue.getStatus() != null){
                    existingIssueEntity.setStatus(issue.getStatus());
                }
                if(issue.getDescription() != null){
                    existingIssueEntity.setDescription(issue.getDescription());
                }
                if(issue.getTitle() != null){
                    existingIssueEntity.setTitle(issue.getTitle());
                }
                existingIssueEntity.setUpdatedDate(getCurrentDate());
                existingIssueEntity = issueRepository.save(existingIssueEntity);
                return toIssue(existingIssueEntity);
            }else{
                logger.warn(ISSUE_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    @Transactional
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
            Optional<IssueEntity> issue = issueRepository.findById(id);
            if(issue.isPresent()){
                IssueEntity issueEntity = issue.get();
                return toIssue(issueEntity);
            }else{
                logger.warn(ISSUE_ID_NOT_FOUND, id);
                throw new RuntimeException(NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error(OPERATION_FAILED, e.getMessage(), e);
            throw new RuntimeException(INTERNAL_SERVER_ER, e);
        }
    }

    private boolean validateAddIssueRequest(Issue issue) {
        return issue.getTitle() != null;
    }
}
