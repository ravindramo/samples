package com.cybage.demo.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This will have all configuration details, populates values while application startup.
 */
public class EmployeeConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private final MongoDBConfiguration dbConfiguration = new MongoDBConfiguration();

    public MongoDBConfiguration getDbConfiguration() {
        return dbConfiguration;
    }
}
