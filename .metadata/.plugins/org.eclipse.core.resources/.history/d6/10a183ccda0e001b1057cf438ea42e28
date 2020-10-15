package com.esprit.microservices;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class Job {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	@Column(name="service")
	private String service;
	@Column(name="etat")
	private boolean etat;
	
	
	
	
	public Job() {
		super();
	}
	public Job(int id, String service, boolean etat) {
		super();
		this.id = id;
		this.service = service;
		this.etat = etat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	
}
