package com.cegeka.tag.tagapi.controller;

import com.cegeka.tag.tagapi.dto.ProjectDTO;
import com.cegeka.tag.tagapi.model.Project;
import com.cegeka.tag.tagapi.service.ProjectService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

  private ProjectService projectService;
  private ModelMapper modelMapper;

  @Autowired
  public ProjectController(ProjectService projectService, ModelMapper modelMapper) {
    this.projectService = projectService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/project")
  @ResponseBody
  public ProjectDTO save(@RequestBody ProjectDTO projectDTO) {
    Project project = this.modelMapper.map(projectDTO, Project.class);
    Project newProject = this.projectService.save(project);

    return modelMapper.map(newProject, ProjectDTO.class);
  }

  @GetMapping("/project/list")
  @ResponseBody
  public List<ProjectDTO> findAll() {
    List<Project> projectList = this.projectService.findAll();
    List<ProjectDTO> projectDTOList = new ArrayList<>();
    projectList.forEach((item) -> {
      ProjectDTO projectDTO = modelMapper.map(item, ProjectDTO.class);
      projectDTOList.add(projectDTO);
    });

    return projectDTOList;
  }

  @DeleteMapping("/project/{id}")
  @ResponseBody
  public Boolean delete(@PathVariable String id) {
    this.projectService.delete(id);

    return true;
  }
}
