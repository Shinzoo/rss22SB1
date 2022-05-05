package fr.univrouen.rss22.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Long> {
	Item findById(String Id);

    List<Item> findByTitle(String title);

    List<Item> findByPublished(String published);
}
