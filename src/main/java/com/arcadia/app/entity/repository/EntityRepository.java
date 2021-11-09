package com.arcadia.app.entity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.arcadia.app.entity.model.SimpleEntity;
import com.arcadia.app.entity.model.SimpleEntityStatus;

public interface EntityRepository extends JpaRepository<SimpleEntity, Long> {

	// Custom finder methods

	/**
	 * Returns all active entities based
	 * 
	 * @return list of active entities
	 * 
	 */
	List<SimpleEntity> findActiveEntities(SimpleEntityStatus status);

	/**
	 * Returns all Entities which name contains entered String
	 * 
	 * @param name string which will be used as a criteria
	 * @return list of entities
	 */
	List<SimpleEntity> findByNameContaining(String name);

	/**
	 * Find Entities in pages, Criteria: Status
	 * 
	 */
	Page<SimpleEntity> findActiveEntities(SimpleEntityStatus status, Pageable pageable);

	/**
	 * Find Entities in pages, Criteria: containing "name"
	 * 
	 */
	Page<SimpleEntity> findByNameContaining(String name, Pageable pageable);

}
