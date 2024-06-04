package com.clc.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;

    private String username;

    private String name;

    private String skills;

    private String skillsToLearn;

    private String password;

    @ManyToMany
    @JoinTable(
            name="assigned_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private List<Project> projectSet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comments> comments;

    @OneToMany(mappedBy = "projectOwner")
    private List<Project> createdProjects;

}
