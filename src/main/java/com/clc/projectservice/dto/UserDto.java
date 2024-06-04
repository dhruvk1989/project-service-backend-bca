package com.clc.projectservice.dto;

import com.clc.projectservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long user_id;

    private String email;

    private String username;

    private String name;

    private String skills;

    private String skillsToLearn;

    public UserDto(User user){
        this.user_id = user.getUser_id();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.name = user.getName();
        this.skills = user.getSkills();
        this.skillsToLearn = user.getSkillsToLearn();
    }

}
