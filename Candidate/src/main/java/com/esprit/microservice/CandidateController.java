package com.esprit.microservice;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CandidateController {

	@Autowired
	private CandidatRepository candidatrepository;
	
	//Récuperer tous les Jobs
	@GetMapping("/candidats")
	public List<Candidat> getallJobs(){
	return candidatrepository.findAll();	
	}
	
	//Créer un job
	@PostMapping("/candidats")
	public Candidat createJob(@Valid @RequestBody Candidat job) {
		return candidatrepository.save(job);
	}
	
	//get job by id

	@GetMapping("/candidats/{id}")
	public ResponseEntity<Candidat> getEmployeeById(@PathVariable(value = "id") int id)
			throws ResourceNotFoundException {
		Candidat candidat = candidatrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job not found for this id :: " + id));
		return ResponseEntity.ok().body(candidat);
	}
	//mette à jour un job
	@PutMapping("/candidats/{id}")
	public ResponseEntity<Candidat> updateEtatJob(@PathVariable(value = "id") int id,
			@Valid @RequestBody Candidat jobDetails) throws ResourceNotFoundException {
		Candidat job = candidatrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		job.setNom(jobDetails.getNom());
job.setPrenom(jobDetails.getPrenom());
		
		final Candidat updateEtatJob = candidatrepository.save(job);
		return ResponseEntity.ok(updateEtatJob);
	}
	//à ne pas oublier (Taskill pour le proccessus qui utilise le port 8080)
}
