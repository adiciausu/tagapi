package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, String> {

}
