package com.cybage.demo.service;

import com.cybage.demo.configuration.EmployeeConfiguration;
import com.cybage.demo.configuration.MongoDBConfiguration;
import com.cybage.demo.core.EmployeeService;
import com.cybage.demo.core.EmployeeServiceImpl;
import com.cybage.demo.core.MongoManaged;
import com.cybage.demo.core.dao.EmployeeDaoMongoImpl;
import com.cybage.demo.filters.IncomingRequestFilter;
import com.cybage.demo.heathcheck.MongoHealthCheck;
import com.cybage.demo.heathcheck.ServiceHealthCheck;
import com.cybage.demo.resource.EmployeeResource;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This is main class to start the application
 */
public class EmployeeDemoService extends Service<EmployeeConfiguration> {
    private static final String SERVICE_NAME = "dropwizard-example";

    public static void main(String[] argv) throws Exception {
        String[] params = {"server", "config/local.yml"};
        new EmployeeDemoService().run(params);
    }


    @Override
    public void initialize(Bootstrap<EmployeeConfiguration> bootstrap) {
        bootstrap.setName(SERVICE_NAME);

        // Turn of writing of dates as timestamps so we default to ISO8601 format (joda time)
        bootstrap.getObjectMapperFactory().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run(EmployeeConfiguration config,
                    Environment environment) throws Exception {

        // add Incoming request filter to environment
        environment.addFilter(new IncomingRequestFilter(), "/*");

        // creating Mongo Client
        final MongoDBConfiguration dbConfig = config.getDbConfiguration();
        MongoClient mongo = new MongoClient(dbConfig.getHost(), dbConfig.getPort());
        DB db = mongo.getDB(dbConfig.getDatabase());

        // add Mongo managed resource to environment
        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.manage(mongoManaged);

        // add Mongo Database health check
        environment.addHealthCheck(new MongoHealthCheck(mongo));

        // adding Employee Resource to environment.
        final EmployeeService authService = new EmployeeServiceImpl(new EmployeeDaoMongoImpl(db));
        environment.addResource(new EmployeeResource(authService));

        // add service health check
        environment.addHealthCheck(new ServiceHealthCheck(authService));
    }
}
