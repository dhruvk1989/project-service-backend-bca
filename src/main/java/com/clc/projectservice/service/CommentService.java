package com.clc.projectservice.service;

import com.clc.projectservice.dto.CommentDto;
import com.clc.projectservice.dto.ProjectDto;
import com.clc.projectservice.dto.UserDto;
import com.clc.projectservice.entity.Comments;
import com.clc.projectservice.entity.Project;
import com.clc.projectservice.entity.User;
import com.clc.projectservice.repo.CommentRepo;
import com.clc.projectservice.repo.ProjectRepo;
import com.clc.projectservice.repo.UserRepo;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    public CommentDto createComment(Long projectId, CommentDto commentDto, User user) throws Exception {

        Comments comments = new Comments();
        comments.setComment(commentDto.getComment());
        comments.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        comments.setUser(user);

        Comments save = commentRepo.save(comments);

        Optional<Project> byId = projectRepo.findById(projectId);

        save.setProject(byId.get());

        Comments comment = commentRepo.save(save);

        return new CommentDto(comment.getCommentId(), comment.getComment(), new UserDto(comment.getUser()), comment.getCreatedTime());


    }

}
