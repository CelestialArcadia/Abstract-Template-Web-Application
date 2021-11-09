package com.arcadia.app.entity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entity")
public class SimpleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private SimpleEntityStatus status;

	public SimpleEntity() {

	}

	/**
	 * Entity's constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param status      Active/Inactive
	 */
	public SimpleEntity(String name, String description, SimpleEntityStatus status) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public SimpleEntityStatus isStatus() {
		return status;
	}

	/**
	 * @param status the status to set Active/Inactive
	 */
	public void setStatus(SimpleEntityStatus status) {
		this.status = status;
	}

}
