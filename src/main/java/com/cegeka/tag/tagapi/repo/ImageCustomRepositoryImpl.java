package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
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
}
