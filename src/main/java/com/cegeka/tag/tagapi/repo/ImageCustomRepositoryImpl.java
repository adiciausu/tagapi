package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.model.ImageStatus;
import com.mongodb.client.result.UpdateResult;
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

  public UpdateResult lockBatch(String projectId, String userId, int count) {
    Criteria projectCriteria = Criteria.where("projectId").is(projectId);
    Criteria statusCriteria = Criteria.where("status").is(ImageStatus.PENDING.toString());

    Query query = new Query();
    query.addCriteria(statusCriteria);
    query.addCriteria(projectCriteria);

    Update update = new Update();
    update.set("status", ImageStatus.PROCESSING.toString());
    update.set("processorUserId", userId);

    FindAndModifyOptions options = new FindAndModifyOptions();
    options.returnNew(true);

    UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Image.class);

    return updateResult;
  }
}
