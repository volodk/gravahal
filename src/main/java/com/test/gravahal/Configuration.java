package com.test.gravahal;

import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 2:46:27 PM 

public class Configuration extends io.dropwizard.Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

}
