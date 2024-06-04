package com.clc.projectservice.repo;

import com.clc.projectservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {



}
