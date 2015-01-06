package com.test.gravahal;

import io.dropwizard.testing.junit.DropwizardAppRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 4:10:01 PM 

public class GravaHalGameDemoTest {
    
    private static final String configLocation;
    static {
        configLocation = GravaHalGameDemoTest.class.getResource("/game-settings.yml").getPath();
    }
    
    private Client client;

    @Rule
    public RuleChain chain = RuleChain.outerRule(
            new DropwizardAppRule<>(DemoGameApplication.class, configLocation)).around(new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            client = Client.create();
        }
        protected void after() {
            client.destroy();
        };
    });
    
    @Test
    public void sampleGameFlow() throws Exception{
        ClientResponse createGameResp = client.resource("http://localhost:8080/game").post(ClientResponse.class);
        System.out.println(createGameResp.getEntity(String.class));
        
        ClientResponse statusResp = client.resource("http://localhost:8080/game/1").get(ClientResponse.class);
        System.out.println(statusResp.getEntity(String.class));
        
        // move the 6th pit of the 1st player
        ClientResponse maveMoveResp = client.resource("http://localhost:8080/game/1/move/1/6").post(ClientResponse.class);
        System.out.println(maveMoveResp.getEntity(String.class));
    }

}
