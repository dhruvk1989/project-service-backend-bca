package com.clc.projectservice.service;

import com.clc.projectservice.dto.ProjectDto;
import com.clc.projectservice.entity.User;
import com.clc.projectservice.repo.ProjectRepo;
import com.clc.projectservice.entity.Project;
import com.clc.projectservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Project> getAllProjects(){
        return projectRepo.findAll();
    }

    public ProjectDto getProject(Long projectId, String getComments){
        Optional<Project> byId = projectRepo.findById(projectId);
        if(byId.isPresent()){
            Project project = byId.get();
            ProjectDto projectDto = new ProjectDto(project, getComments);
            return projectDto;
        }else{
            return null;
        }
    }

    public void createProject(ProjectDto projectDto, User user) {
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setSkills(projectDto.getSkills());

        projectRepo.save(project);

        user.getProjectSet().add(project);
        userRepo.save(user);

        project.setProjectOwner(user);
        projectRepo.save(project);

    }
}
