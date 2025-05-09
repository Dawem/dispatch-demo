package com.example.job_service.controller;

import com.example.model.Job;
import com.example.job_service.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        try {
            Job createdJob = jobService.createJob(job);
            logger.info("Job created with ID: {}", createdJob.getId());
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating job: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJob(@PathVariable String jobId, @RequestBody Job job) {
        try {
            Job updatedJob = jobService.updateJob(jobId, job);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error updating job: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating job: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable String jobId) {
        try {
            jobService.deleteJob(jobId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error deleting job: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting job: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllJobs() {
        try {
            List<Job> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching jobs: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<?> getJobById(@PathVariable String jobId) {
        try {
            Job job = jobService.getJobById(jobId);
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error fetching job: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error fetching job: ", e);
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}