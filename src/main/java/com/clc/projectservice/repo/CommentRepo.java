package com.clc.projectservice.repo;

import com.clc.projectservice.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comments, Long> {



}
