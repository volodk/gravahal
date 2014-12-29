package com.test.gravahal;

import com.test.gravahal.resource.GravaHalResource;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 2:46:27 PM 

public class Application extends io.dropwizard.Application<Configuration> {

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(GravaHalResource.class);
    }

}
