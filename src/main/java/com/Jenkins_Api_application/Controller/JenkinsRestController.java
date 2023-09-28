package com.Jenkins_Api_application.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Jenkins_Api_application.Service.JenkinsPipelineService;




@RestController
@RequestMapping("/api/jenkins")
@CrossOrigin(origins = "*")

public class JenkinsRestController {
	
	
    @Autowired
    private JenkinsPipelineService jenkinsPipelineService;

    @GetMapping("/jobs")
    public ResponseEntity<List<String>> getAllJobs( Model model) {
        try {
            List<String> jobNames = jenkinsPipelineService.getAllJobs();
            System.out.println(jobNames);
            model.addAttribute("msg", jobNames);
            return new ResponseEntity<>(jobNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/createPipeline")
    public String createPipeline(
            @RequestParam String pipelineName,
            @RequestParam String pipelineDescription,
            @RequestParam String gitRepoUrl,
            @RequestParam String gitBranch,
            @RequestParam String jenkinsfilePath,
            Model model) {

        try {
            String pipelineXml = jenkinsPipelineService.generatePipelineXml(
                    pipelineName, pipelineDescription, gitRepoUrl, gitBranch, jenkinsfilePath);

            boolean success = jenkinsPipelineService.createPipeline(pipelineName, pipelineXml);
            
            if (success) {
                model.addAttribute("message", "Pipeline created successfully!");
            } else {
                model.addAttribute("message", "Failed to create the pipeline.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred: ");
            e.printStackTrace();
        }
        
        return "Pipeline created sucessfully with name :" +pipelineName;
    }
    
    @PostMapping("/triggerBuild")
    public ResponseEntity<String> triggerBuild(@RequestParam String pipelineName) {
        try {
            boolean success = jenkinsPipelineService.triggerBuild(pipelineName);
            
            if (success) {
                return new ResponseEntity<>("Build triggered successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to trigger the build.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
