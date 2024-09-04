package com.fixitnow.fix_it_now.repository;

import com.fixitnow.fix_it_now.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
