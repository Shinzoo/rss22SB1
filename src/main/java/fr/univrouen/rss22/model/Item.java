package fr.univrouen.rss22.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.bind.annotation.XmlAccessType;

@Document(collection = "item")
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.NONE)
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Indexed(unique=true)
	@Field(value ="guid")
	@XmlAttribute
	@Id
	private Long id;
	
	@Field(value = "title")
	@XmlElement
	private String title;
	@Field(value = "published")
	@XmlElement
	private String published;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public Item(Long guid, String title, String published) {
		super();
		this.id = guid;
		this.title = title;
		this.published = published;
	}
	
	public Item() {
	}
	
	@Override
	public String toString() {
		return ("Article : " + title + "\n(" + id 
				+ ") Le = " + published );
	}
}