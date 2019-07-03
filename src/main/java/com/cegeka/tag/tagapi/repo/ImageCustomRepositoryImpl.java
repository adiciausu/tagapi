package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.model.ImageStatus;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ImageCustomRepositoryImpl implements ImageCustomRepository {

  private MongoTemplate mongoTemplate;

  @Autowired
  public ImageCustomRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void deleteClassFromImages(String classId) {
    Query query = new Query(Criteria.where("annotations.".concat(classId)).exists(true));
    Update update = new Update();
    update.unset("annotations.".concat(classId));

    mongoTemplate.updateMulti(query, update, Image.class);
  }

  public List<Image> getAndLockBatch(String projectId, String userId, List<String> excludedIds, int count) {
    Criteria projectCriteria = Criteria.where("projectId").is(projectId);
    Criteria statusCriteria = Criteria.where("status").is(ImageStatus.PENDING.toString());
    Criteria excludeIds = Criteria.where("id").nin(excludedIds);

    Query query = new Query();
    query.addCriteria(statusCriteria);
    query.addCriteria(projectCriteria);
    query.addCriteria(excludeIds);
    query.limit(count);
    query.skip(0);

    List<Image> newImages = mongoTemplate.find(query, Image.class);
    List<String> newImageIdList = new ArrayList<>();
    newImages.forEach((Image image) -> {
      image.setProcessorUserId(userId);
      image.setStatus(ImageStatus.PROCESSING);
      newImageIdList.add(image.getId());
    });

    Query updateByIdsQuery = new Query(Criteria.where("id").in(newImageIdList));
    Update update = new Update();
    update.set("status", ImageStatus.PROCESSING.toString());
    update.set("processorUserId", userId);

    UpdateResult updateResult = mongoTemplate.updateMulti(updateByIdsQuery, update, Image.class);

    return newImages;
  }
}
