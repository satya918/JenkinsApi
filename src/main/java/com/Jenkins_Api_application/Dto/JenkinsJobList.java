package com.Jenkins_Api_application.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsJobList {
	
	 @JsonProperty("jobs")
	    private List<JenkinsJob> jobs;

	public List<JenkinsJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<JenkinsJob> jobs) {
		this.jobs = jobs;
	}
	 

}
