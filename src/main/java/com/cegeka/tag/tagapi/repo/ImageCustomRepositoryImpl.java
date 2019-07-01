package com.cegeka.tag.tagapi.repo;

import com.cegeka.tag.tagapi.model.Image;
import com.cegeka.tag.tagapi.model.ImageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

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

    public List<Image> getAndLockBatch(String projectId, String userId, int count) {
        Criteria projectCriteria = Criteria.where("projectId").is(projectId);
        Criteria statusCriteria = Criteria.where("status").is(ImageStatus.PENDING);
        Criteria userCriteria = Criteria.where("userId").not().is(userId);

        Query query = new Query();
        query.addCriteria(statusCriteria);
        query.addCriteria(projectCriteria);
        query.addCriteria(userCriteria);
        query.skip(0);
        query.limit(count);

        Update update = new Update();
        update.set("status", ImageStatus.PROCESSING);

        List<Image> imageList = mongoTemplate.findAndModify(query, update, List.class);

        return imageList;
    }

    public List<Image> getLockedBatch(String projectId, String userId, int count) {
        Criteria projectCriteria = Criteria.where("projectId").is(projectId);
        Criteria statusCriteria = Criteria.where("status").is(ImageStatus.PENDING.toString());
        Criteria userCriteria = Criteria.where("userId").is(userId);

        Query query = new Query();
        query.addCriteria(statusCriteria);
        query.addCriteria(projectCriteria);
        query.addCriteria(userCriteria);
        query.skip(0);
        query.limit(count);

        Update update = new Update();
        update.set("status", ImageStatus.PROCESSING.toString());

        List<Image> imageList = mongoTemplate.findAndModify(query, update, List.class);

        return imageList;
    }
}
