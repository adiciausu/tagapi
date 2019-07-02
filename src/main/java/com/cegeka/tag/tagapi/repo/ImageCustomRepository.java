package com.cegeka.tag.tagapi.repo;

import com.mongodb.client.result.UpdateResult;

public interface ImageCustomRepository {

  public void deleteClassFromImages(String classId);

  public UpdateResult lockBatch(String projectId, String userId, int count);
}
