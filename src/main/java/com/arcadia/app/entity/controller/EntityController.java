package com.arcadia.app.entity.controller;

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

import com.arcadia.app.entity.model.SimpleEntity;
import com.arcadia.app.entity.model.SimpleEntityStatus;
import com.arcadia.app.entity.repository.EntityRepository;

// @CrossOrigin is for configuring allowed origins
@CrossOrigin(origins = "http://localhost:8081")

// @RestController annotation is used to define a controller and to indicate that the 
// return value of the methods should be bound to the web response body.
@RestController

// @RequestMapping("/api") declares that all Apis’ url in the controller will start with /api.
@RequestMapping("/api")
public class EntityController {

	// @Autowired was used to inject EntityRepository bean to local variable
	@Autowired
	EntityRepository entityRepository;

	@GetMapping("/entities")
	public ResponseEntity<List<SimpleEntity>> getAllEntities(@RequestParam(required = false) String name) {

		try {

			List<SimpleEntity> entities = new ArrayList<>();

			if (name == null)
				entityRepository.findAll().forEach(entities::add);
			else
				entityRepository.findByNameContaining(name).forEach(entities::add);

			if (entities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(entities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/entities/{id}")
	public ResponseEntity<SimpleEntity> getEntityById(@PathVariable("id") long id) {

		Optional<SimpleEntity> entityData = entityRepository.findById(id);

		if (entityData.isPresent()) {
			return new ResponseEntity<>(entityData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/entities")
	public ResponseEntity<SimpleEntity> createEntity(@RequestBody SimpleEntity entity) {

		try {

			SimpleEntity entityCreated = entityRepository
					.save(new SimpleEntity(entity.getName(), entity.getDescription(), entity.isStatus()));

			return new ResponseEntity<>(entityCreated, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/entities/{id}")
	public ResponseEntity<SimpleEntity> updateEntity(@PathVariable("id") long id,
			@RequestBody SimpleEntity newEntityData) {

		Optional<SimpleEntity> entityData = entityRepository.findById(id);

		if (entityData.isPresent()) {

			SimpleEntity entityUpdated = entityData.get();
			entityUpdated.setName(newEntityData.getName());
			entityUpdated.setDescription(newEntityData.getDescription());
			entityUpdated.setStatus(newEntityData.isStatus());

			return new ResponseEntity<>(entityRepository.save(entityUpdated), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/entities/{id}")
	public ResponseEntity<HttpStatus> deleteEntity(@PathVariable("id") long id) {
		try {
			entityRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/entities")
	public ResponseEntity<HttpStatus> deleteAllEntities() {
		try {
			entityRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/entities/status")
	public ResponseEntity<List<SimpleEntity>> findActiveEntites() {

		try {
			List<SimpleEntity> entities = entityRepository.findActiveEntities(SimpleEntityStatus.ACTIVE);

			if (entities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(entities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
