package com.chitkara.javachallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.chitkara.javachallenge.service.WebhookService;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    @Override
    public void run(String... args) {
        webhookService.executeWorkflow();  
    }
}
