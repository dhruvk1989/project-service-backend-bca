package com.clc.projectservice.dto;

import com.clc.projectservice.entity.Comments;
import com.clc.projectservice.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private Long projectId;
    private String title;
    private String description;
    private String skills;
    private List<CommentDto> commentsList;
    private UserDto projectOwner;

    private List<Long> joinedUsers;

    public ProjectDto(Project project, String getComments){

        this.projectId = project.getProjectId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.projectOwner = new UserDto(project.getProjectOwner());
        if(getComments.equals("true")){
            this.commentsList = project.getComments().stream().map(a -> {
                return new CommentDto(a.getCommentId(), a.getComment(), new UserDto(a.getUser()), a.getCreatedTime());
            }).collect(Collectors.toList());
        }

        this.joinedUsers = project.getUserSet().stream().map(a -> {
            return a.getUser_id();
        }).collect(Collectors.toList());

    }

}
