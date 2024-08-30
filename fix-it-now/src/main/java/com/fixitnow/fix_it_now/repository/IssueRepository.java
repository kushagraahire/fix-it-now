package com.fixitnow.fix_it_now.repository;

import com.fixitnow.fix_it_now.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
