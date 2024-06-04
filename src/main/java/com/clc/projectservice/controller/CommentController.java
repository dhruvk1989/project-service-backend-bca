package com.clc.projectservice.controller;

import com.clc.projectservice.dto.CommentDto;
import com.clc.projectservice.entity.User;
import com.clc.projectservice.service.AuthHandler;
import com.clc.projectservice.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/project")
public class CommentController {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private CommentService commentService;

    @PostMapping("/{projectId}/comment")
    public ResponseEntity createComment(HttpServletRequest httpServletRequest,
                                        @PathVariable Long projectId,
                                        @RequestBody CommentDto commentDto){

        String authorization = httpServletRequest.getHeader("Authorization");
        boolean authorized = false;

        User user = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            try {
                List response = authHandler.authHandler(authorization.substring(7));
                authorized = (boolean) response.get(0);
                user = (User) response.get(1);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }catch (Exception e){
                e.printStackTrace();
                if(e.getMessage().startsWith("401")){
                    return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
                }else{
                    return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        if(!authorized){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        try {
            CommentDto comment = commentService.createComment(projectId, commentDto, user);
            return new ResponseEntity(comment, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
