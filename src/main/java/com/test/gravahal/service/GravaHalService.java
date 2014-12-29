package com.test.gravahal.service;

import com.test.gravahal.repository.GravaHalRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:47:27 PM 

public class GravaHalService {
    
    private GravaHalRepository repository;
    
    public GravaHalService(GravaHalRepository repository) {
        this.repository = repository;
    }
    
    public int getNextGameId(){
        return 0;
    }
}
