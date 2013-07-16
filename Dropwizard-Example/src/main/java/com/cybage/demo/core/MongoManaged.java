package com.cybage.demo.core;

import com.google.common.base.Preconditions;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MongoManaged implements Managed {
    private static final Logger LOG = LoggerFactory.getLogger(MongoManaged.class);
    private MongoClient mongoClient;

    public MongoManaged(MongoClient mongoClient) {
        this.mongoClient = Preconditions.checkNotNull(mongoClient);
    }

    @Override
    public void start() throws Exception {
        // this is not implemented as the resource need an already initialized Mongo Client
    }

    @Override
    public void stop() throws Exception {
        // Closing MongoClient
        LOG.info("Closing Mongo client..!");
        mongoClient.close();
    }
}
