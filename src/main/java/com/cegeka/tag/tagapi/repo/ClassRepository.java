package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.model.Image;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends PagingAndSortingRepository<Class, String> {
  List<Class> findAllByProjectId(String projectId);
  void deleteByProjectId(String projectId);
}
