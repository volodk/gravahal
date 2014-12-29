package com.test.gravahal;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 4:10:01 PM 

public class GravaHalGameDemoTest {
    
    private static final String PORT = "7090";
    private static final String BASE_URL = MessageFormat.format("http://localhost:{0}", PORT);
    
    private static final String configLocation;
    static {
        configLocation = GravaHalGameDemoTest.class.getResource("/game-settings.yml").getPath();
    }

    private final static ObjectMapper mapper = Jackson.newObjectMapper();
    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    private Client client;

    @Rule
    public RuleChain chain = RuleChain.outerRule(
            new DropwizardAppRule<>(Application.class, configLocation)).around(new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            ClientConfig clientConfig = new DefaultClientConfig();
            JacksonJsonProvider provider = new JacksonJsonProvider();
            provider.setMapper(mapper);
            clientConfig.getSingletons().add(provider);
            client = Client.create(clientConfig);
        }

        protected void after() {
            client.destroy();
        };
    });
    
    @Test
    public void correctGameFlow() throws Exception{
        
    }
    
    @Test
    public void wrongGameFlow() throws Exception{
        
    }

}
