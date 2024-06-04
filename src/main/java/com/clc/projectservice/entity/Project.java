package com.clc.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String title;

    @Column()
    private String description;

    private String skills;

    @ManyToMany(mappedBy = "projectSet")
    @ToString.Exclude
    @JsonIgnore
    private List<User> userSet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Comments> comments;

    @ManyToOne
    private User projectOwner;

    private Timestamp createdDate;

}
