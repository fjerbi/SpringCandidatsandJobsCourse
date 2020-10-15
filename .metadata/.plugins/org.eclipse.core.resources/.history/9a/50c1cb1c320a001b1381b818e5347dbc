package com.esprit.jobs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.jobs.Exception.ResourceNotFoundException;
import com.esprit.jobs.model.Job;
import com.esprit.jobs.repository.JobRepository;

@RestController
@RequestMapping("/api/v1")
public class JobController {

	@Autowired
	private JobRepository jobrepository;
	
	//Récuperer tous les Jobs
	@GetMapping("/jobs")
	public List<Job> getallJobs(){
	return jobrepository.findAll();	
	}
	
	//Créer un job
	@PostMapping("/jobs")
	public Job createJob(@Valid @RequestBody Job job) {
		return jobrepository.save(job);
	}
	
	//get job by id

	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> getEmployeeById(@PathVariable(value = "id") int id)
			throws ResourceNotFoundException {
		Job job = jobrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job not found for this id :: " + id));
		return ResponseEntity.ok().body(job);
	}
	//mette à jour un job
	@PutMapping("/jobs/{id}")
	public ResponseEntity<Job> updateEtatJob(@PathVariable(value = "id") int id,
			@Valid @RequestBody Job jobDetails) throws ResourceNotFoundException {
		Job job = jobrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		job.setService(jobDetails.getService());
		
		final Job updateEtatJob = jobrepository.save(job);
		return ResponseEntity.ok(updateEtatJob);
	}
	//à ne pas oublier (Taskill pour le proccessus qui utilise le port 8080)
	
	//supprimer job
	@DeleteMapping("/jobs/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") int id)
			throws ResourceNotFoundException {
		Job job = jobrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		jobrepository.delete(job);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
