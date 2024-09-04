package com.fixitnow.fix_it_now.Mapper;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import com.fixitnow.fix_it_now.Entity.UserEntity;
import com.fixitnow.fix_it_now.model.Issue;
import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmailAddress(userEntity.getEmailAddress());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRole());
        user.setStatus(userEntity.getStatus());
        user.setProfilePicture(userEntity.getProfilePicture());
        user.setCreatedDate(userEntity.getCreatedDate());
        user.setUpdatedDate(userEntity.getUpdatedDate());

        // Map projectEntities to projects
        if (userEntity.getProjectEntities() != null) {
            List<Project> projects = userEntity.getProjectEntities().stream()
                    .map(ProjectMapper::toProject)
                    .collect(Collectors.toList());
            user.setProjects(projects);
        }

        // Map assignedIssueEntities to assignedIssues
        if (userEntity.getAssignedIssueEntities() != null) {
            List<Issue> assignedIssues = userEntity.getAssignedIssueEntities().stream()
                    .map(IssueMapper::toIssue)
                    .collect(Collectors.toList());
            user.setAssignedIssues(assignedIssues);
        }

        return user;
    }

    public static UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailAddress(user.getEmailAddress());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userEntity.setStatus(user.getStatus());
        userEntity.setProfilePicture(user.getProfilePicture());
        userEntity.setCreatedDate(user.getCreatedDate());
        userEntity.setUpdatedDate(user.getUpdatedDate());

        // Map projects to projectEntities
        if (user.getProjects() != null) {
            List<ProjectEntity> projectEntities = user.getProjects().stream()
                    .map(ProjectMapper::toProjectEntity)
                    .collect(Collectors.toList());
            userEntity.setProjectEntities(projectEntities);
        }

        // Map assignedIssues to assignedIssueEntities
        if (user.getAssignedIssues() != null) {
            List<IssueEntity> assignedIssueEntities = user.getAssignedIssues().stream()
                    .map(IssueMapper::toIssueEntity)
                    .collect(Collectors.toList());
            userEntity.setAssignedIssueEntities(assignedIssueEntities);
        }

        return userEntity;
    }
}
