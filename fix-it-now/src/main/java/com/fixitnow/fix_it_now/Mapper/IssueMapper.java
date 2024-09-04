package com.fixitnow.fix_it_now.Mapper;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.model.Issue;

public class IssueMapper {

    public static Issue toIssue(IssueEntity issueEntity) {
        if (issueEntity == null) {
            return null;
        }

        Issue issue = new Issue();
        issue.setId(issueEntity.getId());
        issue.setTitle(issueEntity.getTitle());
        issue.setDescription(issueEntity.getDescription());
        issue.setStatus(issueEntity.getStatus());
        issue.setProject(ProjectMapper.toProject(issueEntity.getProjectEntity()));
        issue.setAssignedUser(UserMapper.toUser(issueEntity.getAssignedUserEntity()));
        issue.setCreatedDate(issueEntity.getCreatedDate());
        issue.setUpdatedDate(issueEntity.getUpdatedDate());

        return issue;
    }

    public static IssueEntity toIssueEntity(Issue issue) {
        if (issue == null) {
            return null;
        }

        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setId(issue.getId());
        issueEntity.setTitle(issue.getTitle());
        issueEntity.setDescription(issue.getDescription());
        issueEntity.setStatus(issue.getStatus());
        issueEntity.setProjectEntity(ProjectMapper.toProjectEntity(issue.getProject()));
        issueEntity.setAssignedUserEntity(UserMapper.toUserEntity(issue.getAssignedUser()));
        issueEntity.setCreatedDate(issue.getCreatedDate());
        issueEntity.setUpdatedDate(issue.getUpdatedDate());

        return issueEntity;
    }
}

