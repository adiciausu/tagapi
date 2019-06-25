package com.cegeka.tag.tagapi.service;

import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.repo.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassService {

  private ClassRepository classRepository;

  @Autowired
  public ClassService(ClassRepository classRepository) {
    this.classRepository = classRepository;
  }

  public void save(Class clazz) {
    this.classRepository.save(clazz);
  }

  public void delete(String classID) {
    this.classRepository.deleteById(classID);
  }

  public List<Class> findAll(String projectId) {
    List<Class> classList = new ArrayList<>();
    this.classRepository.findAllByProjectId(projectId).forEach(classList::add);

    return classList;
  }
}
