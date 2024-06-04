package com.clc.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String comment;

    @ManyToOne
    @JsonIgnore
    private Project project;

    @ManyToOne
    private User user;

    private Timestamp createdTime;

}
