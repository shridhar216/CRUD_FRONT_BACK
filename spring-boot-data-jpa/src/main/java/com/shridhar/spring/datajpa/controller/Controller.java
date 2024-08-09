package com.shridhar.spring.datajpa.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shridhar.spring.datajpa.entity.MyEntity;
import com.shridhar.spring.datajpa.repository.Repository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")


public class Controller {
	
	@Autowired
	Repository repository;
	
	@GetMapping("/myEntity")
	public ResponseEntity<List<MyEntity>> getAllTutorials(@RequestParam(required=false)String title){
	try {
		List<MyEntity> myEntity = new ArrayList<MyEntity>();
		
		if(title==null)
			repository.findAll().forEach(myEntity::add);
		else
			repository.findByTitleContaining(title).forEach(myEntity::add);
		
		if(myEntity.isEmpty()) {
			return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(myEntity, HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@GetMapping("/myEntity/{id}")
	public ResponseEntity<MyEntity> getTutorialById(@PathVariable("id")long id){
		Optional<MyEntity> myEntityData = repository.findById(id);
		
		if(myEntityData.isPresent()) {
			return new ResponseEntity<>(myEntityData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/myEntity")
	public ResponseEntity<MyEntity> createTutorial(@RequestBody MyEntity myEntity){
		try {
			MyEntity _myEntity = repository.save(new MyEntity(myEntity.getTitle(), myEntity.getDescription(), false));
			return new ResponseEntity<>(_myEntity, HttpStatus.CREATED);
			
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/myEntity/{id}")
	public ResponseEntity<MyEntity>vupdateTutorial(@PathVariable("id")long id, @RequestBody MyEntity myEntity){
		Optional <MyEntity> myEntityData = repository.findById(id);
		
		if(myEntityData.isPresent()) {
			MyEntity _myEntity = myEntityData.get();
			_myEntity.setTitle(myEntity.getTitle());
			_myEntity.setDescription(myEntity.getDescription());
			_myEntity.setPublished(myEntity.isPublished());
			return new ResponseEntity<>(repository.save(_myEntity), HttpStatus.OK);
		} else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
	}
    
	@DeleteMapping("/myEntity/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id){
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/myEntity")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/myEntity/published")
	public ResponseEntity<List<MyEntity>> findByPublished(){
		try {
			List<MyEntity> myEntity = repository.findByPublished(true);
			if(myEntity.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(myEntity, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
