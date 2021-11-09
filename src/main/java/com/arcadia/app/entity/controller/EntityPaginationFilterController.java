package com.arcadia.app.entity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arcadia.app.entity.model.SimpleEntity;
import com.arcadia.app.entity.model.SimpleEntityStatus;
import com.arcadia.app.entity.repository.EntityRepository;

/**
 * In the code, should accept paging parameters using @RequestParam annotation
 * for page, size. By default, 3 Entities will be fetched from database in page
 * index 0.
 * 
 * <p>
 * Next, a Pageable object was created with page & size, check if the name
 * parameter exists or not.
 * 
 * <p>
 * If it is null, Repository findAll(paging) will be called with paging is the
 * Pageable object above.
 * </pre>
 * 
 * <p>
 * If Client sends request with name, use <b>findByNameContaining(name,
 * paging)</b>. Both methods return a Page object.
 * </pre>
 * 
 * <p>
 * 
 * @getContent() to retrieve the List of items in the page.
 * @getNumber() for current Page.
 * @getTotalElements() for total items stored in database.
 * @getTotalPages() for number of total pages.
 *                  </pre>
 *
 */

@RestController
@RequestMapping("/api")
public class EntityPaginationFilterController {

	@Autowired
	EntityRepository entityRepository;

	@GetMapping("/entities")
	public ResponseEntity<Map<String, Object>> getAllEntities(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		try {
			List<SimpleEntity> entities = new ArrayList<>();
			Pageable paging = PageRequest.of(page, size);

			Page<SimpleEntity> pageTuts;
			if (name == null)
				pageTuts = entityRepository.findAll(paging);
			else
				pageTuts = entityRepository.findByNameContaining(name, paging);

			entities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("entities", entities);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/entities/active")
	public ResponseEntity<Map<String, Object>> findActiveEntities(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		try {
			List<SimpleEntity> entities = new ArrayList<>();
			Pageable paging = PageRequest.of(page, size);

			Page<SimpleEntity> pageTuts = entityRepository.findActiveEntities(SimpleEntityStatus.ACTIVE, paging);
			entities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", entities);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}