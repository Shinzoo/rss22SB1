package fr.univrouen.rss22.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long getMaxItemId() {
    	Query query = new Query();
    	query.with(Sort.by(Sort.Direction.DESC, "id"));
    	query.limit(1);
    	Item maxObject = mongoTemplate.findOne(query, Item.class);
    	if (maxObject == null) {
    		return 0L;
    	}
    	return maxObject.getId();
    }

    @Override
    public long updateItem(String guid, String title, String published) {
        Query query = new Query(Criteria.where("guid").is(guid));
        Update update = new Update();
        update.set("title", title);
        update.set("published", published);

        UpdateResult result = this.mongoTemplate.updateFirst(query, update, Item.class);

        if (result != null) {
            return result.getModifiedCount();
        }

        return 0;
    }

}