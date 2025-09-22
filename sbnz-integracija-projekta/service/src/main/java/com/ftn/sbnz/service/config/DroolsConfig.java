package com.ftn.sbnz.service.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    @Autowired
    private KieContainer kieContainer;

    @Bean
    public KieSession fwKsession() {
        return kieContainer.newKieSession("fwKsession");
    }

    @Bean
    public KieSession bwKsession() {
        return kieContainer.newKieSession("bwKsession");
    }

    @Bean
    public KieSession cepKsession() {
        return kieContainer.newKieSession("cepKsession");
    }
}