package com.hruday.loanApproval;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.idm.api.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {

    @Bean
    public CommandLineRunner init(RepositoryService repositoryService, IdentityService identityService) {

        return args -> {
            repositoryService.createDeployment()
                    .name("Loan Application Process")
                    .addClasspathResource("processes/loan-process.bpmn20.xml")
                    .deploy();

            if(identityService.createUserQuery().userId("hruday").count() ==0) {
                User user = identityService.newUser("hruday");
                user.setFirstName("Hruday");
                user.setLastName("Patil");
                user.setPassword("typewriter");
                identityService.saveUser(user);
            }
        };
    }
}
