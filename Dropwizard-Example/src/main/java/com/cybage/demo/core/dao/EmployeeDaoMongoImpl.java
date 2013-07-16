package com.cybage.demo.core.dao;

import com.cybage.demo.core.exception.DatabaseErrorException;
import com.cybage.demo.representations.Employee;
import com.cybage.demo.wrapper.EmployeeWrapper;
import com.google.common.base.Preconditions;
import com.mongodb.*;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Dao class which interacts with MongoDB
 */
public class EmployeeDaoMongoImpl implements EmployeeDao {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDaoMongoImpl.class);

    private final DB db;

    private final Timer getAllEmployeesTimer;
    private final Timer getEmployeeByIdTimer;
    private final Timer createOrUpdateEmployeeTimer;
    private final Timer deleteEmployeeTimer;

    public EmployeeDaoMongoImpl(DB db) {
        this.db = Preconditions.checkNotNull(db);
        getAllEmployeesTimer = Metrics.newTimer(getClass(), "getAllEmployees", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
        getEmployeeByIdTimer = Metrics.newTimer(getClass(), "getEmployeeById", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
        createOrUpdateEmployeeTimer = Metrics.newTimer(getClass(), "createOrUpdateEmployee", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
        deleteEmployeeTimer = Metrics.newTimer(getClass(), "deleteEmployee", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    }

    /**
     * Return all Employees those are exists in the data source
     */
    @Override
    public List<Employee> getAllEmployees() {
        final TimerContext context = getAllEmployeesTimer.time();
        try {
            DBCollection employeeTable = db.getCollection(EMPLOYEE_TABLE);
            List<DBObject> employees = employeeTable.find().toArray();
            List<Employee> allEmployees = new ArrayList<Employee>();

            for (DBObject dbRecord : employees) {
                Employee employee = new Employee();
                employee.setId((String) dbRecord.get(EMPLOYEE_ID));
                employee.setName((String) dbRecord.get(EMPLOYEE_NAME));
                employee.setDesignation((String) dbRecord.get(EMPLOYEE_DESIGNATION));
                allEmployees.add(employee);
            }
            return allEmployees;
        } catch (Exception e) {
            LOG.error("Internal Server Error : ", e);
            throw new DatabaseErrorException(DatabaseErrorException.MESSAGE);
        } finally {
            context.stop();
        }
    }

    /**
     * Returns an employee based on provided ID
     *
     * @param id
     * @return
     */
    @Override
    public Employee getEmployeeById(String id) {
        final TimerContext context = getEmployeeByIdTimer.time();
        try {
            DBCollection authTable = db.getCollection(EMPLOYEE_TABLE);
            DBObject query = new BasicDBObject();
            query.put(EMPLOYEE_ID, id);
            DBObject dbRecord = authTable.findOne(query);
            Employee employee = null;
            if (dbRecord != null) {
                employee = new Employee();
                employee.setId((String) dbRecord.get(EMPLOYEE_ID));
                employee.setName((String) dbRecord.get(EMPLOYEE_NAME));
                employee.setDesignation((String) dbRecord.get(EMPLOYEE_DESIGNATION));
            }
            return employee;
        } catch (Exception e) {
            LOG.error("Internal Server Error : ", e);
            throw new DatabaseErrorException(DatabaseErrorException.MESSAGE);
        } finally {
            Bytes.clearAllHooks();
            context.stop();
        }
    }

    /**
     * Stores a new employee information
     *
     * @param employee
     * @return
     */
    @Override
    public EmployeeWrapper createOrUpdateEmployee(Employee employee) {
        final TimerContext context = createOrUpdateEmployeeTimer.time();
        try {
            final DBCollection authTable = db.getCollection(EMPLOYEE_TABLE);
            DBObject record = new BasicDBObject();
            record.put(EMPLOYEE_ID, employee.getId());
            DBObject existedRecord = authTable.findOne(record);
            record.put(EMPLOYEE_NAME, employee.getName());
            record.put(EMPLOYEE_DESIGNATION, employee.getDesignation());
            boolean existingUser = false;
            if (existedRecord == null) {
                authTable.save(record);
            } else {
                existingUser = true;
                authTable.update(existedRecord, record);
            }

            return new EmployeeWrapper(existingUser, employee);
        } catch (Exception e) {
            LOG.error("Internal Server Error : ", e);
            throw new DatabaseErrorException(DatabaseErrorException.MESSAGE);
        } finally {
            context.stop();
        }
    }

    /**
     * Deletes a particular employee
     *
     * @param id
     */
    @Override
    public void deleteEmployee(String id) {
        final TimerContext context = deleteEmployeeTimer.time();
        try {
            DBCollection authTable = db.getCollection(EMPLOYEE_TABLE);
            DBObject query = new BasicDBObject();
            query.put(EMPLOYEE_ID, id);
            authTable.remove(query);
        } catch (Exception e) {
            LOG.error("Internal Server Error : ", e);
            throw new DatabaseErrorException(DatabaseErrorException.MESSAGE);
        } finally {
            context.stop();
        }
    }
}
