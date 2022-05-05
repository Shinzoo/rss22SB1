package fr.univrouen.rss22.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryCustom{
	public long getMaxItemId();
	public long updateItem(String guid, String title, String published);
}
