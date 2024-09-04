package com.fixitnow.fix_it_now.repository;

import com.fixitnow.fix_it_now.Entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
}
