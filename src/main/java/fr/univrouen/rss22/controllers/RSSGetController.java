package fr.univrouen.rss22.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss22.model.Item;
import fr.univrouen.rss22.model.ItemRepository;
import fr.univrouen.rss22.model.ItemRepositoryCustom;

@RestController
public class RSSGetController {

	@Autowired
	private ItemRepositoryCustom itemRepositoryCustom;

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("/")
	public String index() {
        String html = "";
        html += "RSS22 - Service REST et Client - V1.0 par Gauthier Salas - Thomas Prevost";
        html += "<ul>";
        html += " <li><a href='/help'>url: /help - Méthode GET - Format HTML - Affiche la page contenant les informations d’aide. </a></li>";
        html += "</ul>";
        return html;
    }

	@GetMapping("/accueil")
	public String getIndex(Model model) {
		// Initialisation du message
		String message = "Test Thymeleaf !";
		// Mise en forme de la date
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
		String today = format.format(new Date());
		// Création des attributs pour insertion dans la page HTML via Thymeleaf
		model.addAttribute("message", message);
		model.addAttribute("today", today);
		// Création de la page HTML avec le template "accueil.html"
		return "accueil";
	}

	@GetMapping("/help")
	public String getHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		sb.append(" <li><a href='/help'>url: /help - Méthode GET - Format HTML - Affiche la page contenant les informations d’aide. </a></li>");
		sb.append(" <li><a href='/'>url: / - Accueil</a></li>");
		sb.append(" <li><a href='/rss22/resume/xml'>url: /rss22/resume/xml</a> - Méthode GET - Flux XML - Liste des articles présents dans la base.</li>");
		sb.append(" <li><a href='/rss22/resume/html'>url: /rss22/resume/html </a>  - Méthode GET - Page HTML - Liste des articles présents dans la base.</li>");
		sb.append(" <li><a href='/rss22/html/1'>url: /rss22/html/1 </a>  - Méthode GET -  Flux XML conforme au schéma xsd comprenant un seul article - Affiche le contenu complet de l’article dont l’identifiant est {guid}.</li>");
		sb.append(" <li><a href='/rss22/xml/1'>url: /rss22/xml/1</a>  - Méthode GET - Page HTML - Affiche le contenu complet de l’article dont l’identifiant est {guid}.</li>");
		sb.append(" <li><a href='/rss22/insert'>url: /rss22/insert</a>  - Méthode POST - Flux XML décrivant le flux rss22 à ajouter, conforme au schéma xsd - Si l’opération est réussie, alors le flux est ajouté à la base et sa persistance est assurée.</li>");
		sb.append(" <li><a href='/rss22/delete/1'>url: /rss22/delete/{guid}</a>  - Méthode DELETE - Suppression de l’article dont l’identifiant est {guid}.</li>");
		sb.append("</ul>");
		return (sb.toString());
	}

	@GetMapping(value = "/rss22/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public String getArticlesXML() {
		List<Item> l = new ArrayList<Item>();
		l.addAll(itemRepository.findAll());
		StringBuilder sb = new StringBuilder();
		sb.append("<item> ");
		for (Item item : l) {
			sb.append("<title>" + item.getTitle() + "</title> <date>" + item.getPublished() + "</date> <guid>"
					+ item.getId() + "</guid>");
		}
		sb.append("</item> ");
		return (sb.toString());
	}

	@GetMapping(value = "/rss22/resume/html")
	public String getArticlesHTML() {
		List<Item> l = new ArrayList<Item>();
		l.addAll(itemRepository.findAll());
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=\"1\">");
		sb.append("<th>Titre </th> <th>Date </th> <th>Guid </th>");
		for (Item item : l) {
			sb.append("<tr><td>" + item.getTitle() + "</td> <td>" + item.getPublished() + "</d> <td>" + item.getId()
					+ "</td> </tr>");
		}
		sb.append("</table>");
		return (sb.toString());
	}

	@GetMapping(value = "/rss22/html/{guid}")
	public String getArticlesGUIDHTML(@PathVariable("guid") Long id) {
		StringBuilder sb = new StringBuilder();
		Item i = itemRepository.findById(id).orElse(null);
		sb.append("Titre: " + i.getTitle() + " Date: " + i.getPublished() + " Guid : " + i.getId());
		return (sb.toString());
	}

	@GetMapping(value = "/rss22/xml/{guid}", produces = MediaType.APPLICATION_XML_VALUE)
	public String getArticlesGUIDXML(@PathVariable("guid") Long id) {
		StringBuilder sb = new StringBuilder();
		Item i = itemRepository.findById(id).orElse(null);
		sb.append("<item> ");
		sb.append("<title>" + i.getTitle() + "</title> <date>" + i.getPublished() + "</date> <guid>" + i.getId()
				+ "</guid>");
		sb.append("</item> ");
		return (sb.toString());
	}

	@GetMapping("/rss22/insert")
	public String insert() {
		Item item = new Item();

		long id = this.itemRepositoryCustom.getMaxItemId() + 1;

		String title = "title"+id;
		String published = "published"+id;

		item.setId(id);
		item.setTitle(title);
		item.setPublished(published);
		this.itemRepository.insert(item);

		return "Inserted id: " + item.getId() +" title: "+ item.getTitle() +" date: "+ item.getPublished();
	}

	@DeleteMapping(value = "/rss22/delete/{guid}")
	public ResponseEntity<Long> deleteItemById(@PathVariable("guid") Long id) {
		Item i = itemRepository.findById(id).orElse(null);
		if (i == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			itemRepository.deleteById(id);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}
}