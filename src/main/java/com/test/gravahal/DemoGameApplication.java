package com.test.gravahal;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.test.gravahal.repository.GameRepository;
import com.test.gravahal.repository.PlayerRepository;
import com.test.gravahal.repository.impl.InMemoryGameRepository;
import com.test.gravahal.repository.impl.InMemoryPlayerRepository;
import com.test.gravahal.resource.GameEndpoint;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 2:46:27 PM 

public class DemoGameApplication extends io.dropwizard.Application<Configuration> {

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // no specific initialize procedure is needed
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        
        environment.servlets().setInitParameter("com.sun.jersey.config.property.packages", "com.test.grahaval"); 
        
        // Configuring Guice IOC 
        Injector ioc = Guice.createInjector(new AbstractModule(){
            @Override
            protected void configure() {
                bind(GameRepository.class).to(InMemoryGameRepository.class);
                bind(PlayerRepository.class).to(InMemoryPlayerRepository.class);
            }
        });
        
        // register REST endpoint
        environment.jersey().register( ioc.getInstance(GameEndpoint.class) );
        
        // Register application's health check
        environment.healthChecks().register("basicHealthCheck", ioc.getInstance(GameHealthCheck.class));
    }
    
    // Entry point
    public static void main(String[] args) throws Exception {
        new DemoGameApplication().run(args);
    }

}
