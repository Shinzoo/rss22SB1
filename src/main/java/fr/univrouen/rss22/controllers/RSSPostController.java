package fr.univrouen.rss22.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.ItemRepository;
import fr.univrouen.rss22.model.ItemRepositoryCustom;
import fr.univrouen.rss22.model.TestRSS;

@RestController
public class RSSPostController 
{	
	@Autowired
	private ItemRepositoryCustom itemRepositoryCustom;

	@Autowired
	private ItemRepository itemRepository;
	
	@RequestMapping(value = "/rss22/insert", method = RequestMethod.POST)
	public String testInsert(@RequestParam String title, @RequestParam String published) {
		Item item = new Item();

		long id = this.itemRepositoryCustom.getMaxItemId() + 1;

		item.setId(id);
		item.setTitle(title);
		item.setPublished(published);
		this.itemRepository.insert(item);

		return "Inserted id: " + item.getId() +" title: "+ item.getTitle() +" date: "+ item.getPublished();
	}

}
