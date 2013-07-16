package com.cybage.demo.heathcheck;

import com.mongodb.MongoClient;
import com.yammer.metrics.core.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This gives the  health check of mongo Database
 */
public class MongoHealthCheck extends HealthCheck {
    private static final Logger LOG = LoggerFactory.getLogger(MongoHealthCheck.class);

    private final MongoClient mongo;
    private final String DB_HEALTHY_MSG = "Database is Up";
    private final String DB_UNHEALTHY_MSG = "Database is Down";

    public MongoHealthCheck(MongoClient mongo) {
        super("Employee-Demo-database");
        this.mongo = mongo;
    }

    @Override
    protected Result check() {
        try {
            mongo.getDatabaseNames();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Result.unhealthy(DB_UNHEALTHY_MSG);
        }

        return Result.healthy(DB_HEALTHY_MSG);
    }
}
