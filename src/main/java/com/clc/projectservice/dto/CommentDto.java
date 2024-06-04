package com.clc.projectservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String comment;
    private UserDto userDto;
    private Timestamp timestamp;

}
