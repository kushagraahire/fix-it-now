package com.fixitnow.fix_it_now.repository;

import com.fixitnow.fix_it_now.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
