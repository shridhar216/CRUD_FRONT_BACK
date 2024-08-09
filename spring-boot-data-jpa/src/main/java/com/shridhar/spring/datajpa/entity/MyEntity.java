package com.shridhar.spring.datajpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "Tutorials")

public class MyEntity {
 public MyEntity( String title, String description, boolean published) {
		super();
		
		this.title = title;
		this.description = description;
		this.published = published;
	}

 public MyEntity() {
	super();
}

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id ;
 
 public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public boolean isPublished() {
	return published;
}

public void setPublished(boolean published) {
	this.published = published;
}

@Column(name = "title")
 private String title;
 
 @Column(name="description")
 private String description;
 
 @Column(name= "published")
 private boolean published;

@Override
public String toString() {
	return "Entity [id=" + id + ", title=" + title + ", description=" + description + ", published=" + published + "]";
}
 
 
}
