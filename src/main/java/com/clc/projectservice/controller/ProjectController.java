package com.clc.projectservice.controller;

import com.clc.projectservice.dto.ProjectDto;
import com.clc.projectservice.dto.UserDto;
import com.clc.projectservice.entity.Project;
import com.clc.projectservice.entity.User;
import com.clc.projectservice.repo.UserRepo;
import com.clc.projectservice.service.AuthHandler;
import com.clc.projectservice.service.ProjectService;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.net.UnknownServiceException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("")
    public ResponseEntity getAllProjects(HttpServletRequest httpServletRequest, @RequestParam(required = false, defaultValue = "false") String getComments) {

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
                e.printStackTrace();;
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if(!authorized){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        List allProjects = new ArrayList();

        projectService.getAllProjects().stream().forEach(a -> {
            allProjects.add(new ProjectDto(a, getComments));
        });

        ResponseEntity<List<ProjectDto>> projectList = new ResponseEntity<>(allProjects, HttpStatus.OK);



        return new ResponseEntity<>(projectList, HttpStatus.OK);

    }

    @GetMapping("/myProjects")
    public ResponseEntity getMyProject(HttpServletRequest httpServletRequest, @RequestParam(required = false, defaultValue = "false") String getComments) {

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
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if(!authorized){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Map response = new HashMap();

        User user1 = userRepo.findById(user.getUser_id()).get();

        List<ProjectDto> assignedProject = user1.getProjectSet().stream().map(a -> {
            return new ProjectDto(a, "false");
        }).collect(Collectors.toList());

        List<ProjectDto> myProjects = user1.getCreatedProjects().stream().map(a -> {
            return new ProjectDto(a, "false");
        }).collect(Collectors.toList());

        response.put("joinedProject", assignedProject);
        response.put("createdProjects", myProjects);

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity getProject(HttpServletRequest httpServletRequest,
                                     @RequestParam(required = false, defaultValue = "false") String getComments,
                                     @PathVariable Long projectId) {

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
                e.printStackTrace();;
                return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if(!authorized){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        ProjectDto projectDto = projectService.getProject(projectId, getComments);

        if(projectDto == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            ResponseEntity<ProjectDto> project = new ResponseEntity<>(projectDto, HttpStatus.OK);
            return project;
        }

    }


    @PostMapping("")
    public ResponseEntity createProject(@RequestBody ProjectDto projectDto, HttpServletRequest httpServletRequest){

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
            projectService.createProject(projectDto, user);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
