package com.fixitnow.fix_it_now.repository;

import com.fixitnow.fix_it_now.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
