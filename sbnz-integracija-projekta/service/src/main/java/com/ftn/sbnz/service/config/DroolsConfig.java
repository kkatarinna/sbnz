package com.ftn.sbnz.service.config;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Configuration
public class DroolsConfig {

    @Autowired
    private KieContainer kieContainer;

    @Bean
    public KieSession fwKsession() {
        return kieContainer.newKieSession("fwKsession");
    }

    @Bean
    public KieSession cepKsession() {


        KieSession kieSession = kieContainer.newKieSession("cepKsession");

        Set<String> suspiciousIPs = Set.of("192.168.1.100", "10.0.0.5");
        Set<Integer> suspiciousPorts = Set.of(22, 23, 3389, 4444);


        kieSession.setGlobal("suspiciousIPs", suspiciousIPs);
        kieSession.setGlobal("suspiciousPorts", suspiciousPorts);

        return kieSession;
    }

    @Bean
    public KieSession cepKsession2() {


        KieSession kieSession = kieContainer.newKieSession("cepKsession2");

        return kieSession;
    }


    @Bean
    public KieSession tempKsession() {


        InputStream template = DroolsConfig.class.getResourceAsStream("/templatetable/template.drt");
        InputStream data = DroolsConfig.class.getResourceAsStream("/templatetable/template-data.xls");

        if(template == null || data == null) {
            throw new RuntimeException("Ne mogu da nađem template ili data fajl za tempKsession2");
        }
        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(data, template, 3, 2);


        return this.createKieSessionFromDRL(drl);

    }

    @Bean
    public KieSession tempKsession2() {
        InputStream template;
        InputStream data;
        try{
            template = DroolsConfig.class.getResourceAsStream("/templatetable2/template2.drt");
            data = DroolsConfig.class.getResourceAsStream("/templatetable2/os_vulnerabilities.xls");
            ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
            String drl = converter.compile(data, template, 3, 2);


            return this.createKieSessionFromDRL(drl);
        }catch(Exception e){
            System.out.println("UNABLE TO FIND: " + e.getMessage());
        }
        return null;

    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}