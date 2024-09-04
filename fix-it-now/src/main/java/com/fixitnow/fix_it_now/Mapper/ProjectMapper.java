package com.fixitnow.fix_it_now.Mapper;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import com.fixitnow.fix_it_now.Entity.ProjectEntity;
import com.fixitnow.fix_it_now.Entity.UserEntity;
import com.fixitnow.fix_it_now.model.Issue;
import com.fixitnow.fix_it_now.model.Project;
import com.fixitnow.fix_it_now.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Project toProject(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        Project project = new Project();
        project.setId(projectEntity.getId());
        project.setName(projectEntity.getName());
        project.setUpdatedDate(projectEntity.getUpdatedDate());
        project.setDescription(projectEntity.getDescription());
        project.setCreatedDate(projectEntity.getCreatedDate());

        // Map userEntities to users
        if (projectEntity.getUserEntities() != null) {
            List<User> users = projectEntity.getUserEntities().stream()
                    .map(UserMapper::toUser)
                    .collect(Collectors.toList());
            project.setUsers(users);
        }

        // Map issueEntities to issues
        if (projectEntity.getIssueEntities() != null) {
            List<Issue> issues = projectEntity.getIssueEntities().stream()
                    .map(IssueMapper::toIssue)
                    .collect(Collectors.toList());
            project.setIssues(issues);
        }

        return project;
    }

    public static ProjectEntity toProjectEntity(Project project) {
        if (project == null) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(project.getId());
        projectEntity.setName(project.getName());
        projectEntity.setUpdatedDate(project.getUpdatedDate());
        projectEntity.setDescription(project.getDescription());
        projectEntity.setCreatedDate(project.getCreatedDate());

        // Map users to userEntities
        if (project.getUsers() != null) {
            List<UserEntity> userEntities = project.getUsers().stream()
                    .map(UserMapper::toUserEntity)
                    .collect(Collectors.toList());
            projectEntity.setUserEntities(userEntities);
        }

        // Map issues to issueEntities
        if (project.getIssues() != null) {
            List<IssueEntity> issueEntities = project.getIssues().stream()
                    .map(IssueMapper::toIssueEntity)
                    .collect(Collectors.toList());
            projectEntity.setIssueEntities(issueEntities);
        }

        return projectEntity;
    }
}
