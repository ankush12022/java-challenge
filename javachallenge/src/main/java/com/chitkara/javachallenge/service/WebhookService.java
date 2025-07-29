package com.chitkara.javachallenge.service;

import com.chitkara.javachallenge.model.WebhookRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class WebhookService {

    public void executeWorkflow() {
        RestTemplate restTemplate = new RestTemplate();

        String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        WebhookRequest request = new WebhookRequest();
        request.setName("Ankush");
        request.setRegNo("2210990119");  
        request.setEmail("ankush119.be22@chitkara.edu.in");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        
        ResponseEntity<Map> response = restTemplate.postForEntity(generateUrl, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhookUrl = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

           String finalQuery = "SELECT dept_name, COUNT(*) AS employee_count FROM employee GROUP BY dept_name HAVING COUNT(*) > 2";
  submitFinalQuery(restTemplate, webhookUrl, accessToken, finalQuery);
  
        } else {
            System.out.println("Error: Failed to generate webhook");
        }
    }

    private void submitFinalQuery(RestTemplate restTemplate, String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);  

        Map<String, String> body = Map.of("finalQuery", finalQuery);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);
        System.out.println("Submission Response: " + response.getBody());
    }
}
