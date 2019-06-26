package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Class;
import com.cegeka.tag.tagapi.model.Image;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ImageCustomRepositoryImpl implements ImageCustomRepository {
  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public void deleteClassFromImages(String classId) {
    Query query = new Query(Criteria.where("annotations.".concat(classId)).exists(true));
    Update update = new Update();
    update.unset("annotations.".concat(classId));

    mongoTemplate.updateMulti(query, update, Image.class);
  }
}
