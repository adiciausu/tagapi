package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, String> {


}
