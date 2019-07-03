package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import java.util.List;

public interface ImageCustomRepository {

  public void deleteClassFromImages(String classId);

  public List<Image> getAndLockBatch(String projectId, String userId, List<String> excludedIds,
      int count);
}
