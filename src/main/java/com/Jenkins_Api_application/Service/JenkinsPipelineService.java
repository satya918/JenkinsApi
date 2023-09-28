package com.Jenkins_Api_application.Service;

import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Jenkins_Api_application.Dto.JenkinsJob;
import com.Jenkins_Api_application.Dto.JenkinsJobList;

@Service
public class JenkinsPipelineService {

    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @Value("${jenkins.username}")
    private String jenkinsUsername; 
    @Value("${jenkins.password}")
    private String jenkinsApiToken; 

    public String generatePipelineXml(
            String pipelineName,
            String pipelineDescription,
            String gitRepoUrl,
            String gitBranch,
            String jenkinsfilePath) {

        String pipelineXmlTemplate = "<flow-definition plugin=\"workflow-job@2.42\">\n" +
                "  <actions/>\n" +
                "  <description>${PIPELINE_DESCRIPTION}</description>\n" +
                "  <keepDependencies>false</keepDependencies>\n" +
                "  <properties/>\n" +
                "  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition\" plugin=\"workflow-cps@2.92\">\n" +
                "    <scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@4.11.0\">\n" +
                "      <configVersion>2</configVersion>\n" +
                "      <userRemoteConfigs>\n" +
                "        <hudson.plugins.git.UserRemoteConfig>\n" +
                "          <url>${GIT_REPO_URL}</url>\n" +
                "        </hudson.plugins.git.UserRemoteConfig>\n" +
                "      </userRemoteConfigs>\n" +
                "      <branches>\n" +
                "        <hudson.plugins.git.BranchSpec>\n" +
                "          <name>${GIT_BRANCH}</name>\n" + 
                "        </hudson.plugins.git.BranchSpec>\n" +
                "      </branches>\n" +
                "      <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
                "      <submoduleCfg class=\"list\"/>\n" +
                "      <extensions/>\n" +
                "    </scm>\n" +
                "    <scriptPath>${JENKINSFILE_PATH}</scriptPath>\n" + 
                "    <lightweight>true</lightweight>\n" +
                "  </definition>\n" +
                "  <triggers/>\n" +
                "</flow-definition>";

        String pipelineXml = pipelineXmlTemplate
                .replace("${PIPELINE_DESCRIPTION}", pipelineDescription)
                .replace("${GIT_REPO_URL}", gitRepoUrl)
                .replace("${GIT_BRANCH}", gitBranch)
                .replace("${JENKINSFILE_PATH}", jenkinsfilePath);

        return pipelineXml;
    }

    public boolean createPipeline(String pipelineName, String pipelineXml) throws URISyntaxException {
        String apiUrl = jenkinsUrl + "/createItem?name=" + pipelineName;
        URI uri = new URI(apiUrl);

        HttpHeaders headers = new HttpHeaders();
        String credentials = jenkinsUsername + ":" + jenkinsApiToken;
        byte[] credentialsBytes = credentials.getBytes();
        String credentialsBase64 = Base64.getEncoder().encodeToString(credentialsBytes);
        headers.setBasicAuth(credentialsBase64);

        headers.setContentType(MediaType.APPLICATION_XML);

        HttpMethod httpMethod = HttpMethod.POST;

        RequestEntity<String> requestEntity = new RequestEntity<>(
                pipelineXml,
                headers,
                httpMethod, 
                uri
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return responseEntity.getStatusCode().is2xxSuccessful();
    }
    
    
    public List<String> getAllJobs() throws URISyntaxException {
        String apiUrl = jenkinsUrl + "/api/json";
        URI uri = new URI(apiUrl);

        HttpHeaders headers = new HttpHeaders();
        String credentials = jenkinsUsername + ":" + jenkinsApiToken;
        byte[] credentialsBytes = credentials.getBytes();
        String credentialsBase64 = Base64.getEncoder().encodeToString(credentialsBytes);
        headers.setBasicAuth(credentialsBase64);

        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(
                headers,
                HttpMethod.GET,
                uri
        );
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JenkinsJobList> responseEntity = restTemplate.exchange(
                requestEntity,
                JenkinsJobList.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JenkinsJobList jobList = responseEntity.getBody();
            if (jobList != null && jobList.getJobs() != null) {
                List<String> jobNames = jobList.getJobs().stream()
                        .map(JenkinsJob::getName)
                        .collect(Collectors.toList());
                return jobNames;
            }
        }

        return Collections.emptyList();
    }
    
    public boolean triggerBuild(String pipelineName) throws URISyntaxException {
        String apiUrl = jenkinsUrl + "/job/" + pipelineName + "/build";
        URI uri = new URI(apiUrl);

        HttpHeaders headers = new HttpHeaders();
        String credentials = jenkinsUsername + ":" + jenkinsApiToken;
        byte[] credentialsBytes = credentials.getBytes();
        String credentialsBase64 = Base64.getEncoder().encodeToString(credentialsBytes);
        headers.setBasicAuth(credentialsBase64);

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpMethod httpMethod = HttpMethod.POST;

        RequestEntity<Void> requestEntity = new RequestEntity<>(
                headers,
                httpMethod,
                uri
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return responseEntity.getStatusCode().is2xxSuccessful();
}
}