package com.cegeka.tag.tagapi.service;

import com.cegeka.tag.tagapi.model.Project;
import com.cegeka.tag.tagapi.repo.ProjectRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectService {

  private ProjectRepository projectRepository;

  @Autowired
  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public void save(Project project) {
    this.projectRepository.save(project);
  }

  public List<Project> findAll() {
    List<Project> projectList = new ArrayList<>();
    this.projectRepository.findAll().forEach(projectList::add);

    return projectList;
  }

  public void delete(String imageId) {
    this.projectRepository.deleteById(imageId);
  }
}
