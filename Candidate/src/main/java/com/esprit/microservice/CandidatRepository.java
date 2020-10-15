package com.esprit.microservice;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
	
	



}
