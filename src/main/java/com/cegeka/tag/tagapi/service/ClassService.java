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

  public List<Class> findAll() {
    List<Class> classList = new ArrayList<>();
    this.classRepository.findAll().forEach(classList::add);

    return classList;
  }
}
