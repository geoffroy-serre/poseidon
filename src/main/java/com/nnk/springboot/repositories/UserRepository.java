package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * No method declared. Extends JpaRepository<User, Integer>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

  User findByUsername(String username);

}
