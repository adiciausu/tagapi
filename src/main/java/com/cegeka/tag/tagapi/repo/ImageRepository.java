package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.model.ImageStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, String>,
    ImageCustomRepository {

  List<Image> findAllByProjectId(String projectId);
  List<Image> findAllByProjectIdAndStatusAndProcessorUserId(String projectId, ImageStatus status,
      String userId, Pageable pageable);

  void deleteByProjectId(String projectId);
}
