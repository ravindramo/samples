package com.cybage.demo.heathcheck;

import com.cybage.demo.core.EmployeeService;
import com.cybage.demo.representations.Employee;
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
public class ServiceHealthCheck extends HealthCheck {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceHealthCheck.class);
    private final EmployeeService service;
    private final String EMPLOYEE_ID = "10594";
    private final String EMPLOYEE_NAME = "Srinivas Koppula";
    private final String EMPLOYEE_DESIGNATION = "Software Engineer";
    protected final static String HEALTH_CHECK_UP = " UP ";
    protected final static String HEALTH_CHECK_DOWN = " Down ";

    private static boolean isHealthy;

    public ServiceHealthCheck(EmployeeService service) {
        super("employee-demo-service");
        this.service = service;
    }

    @Override
    protected Result check() throws Exception {
        String msg = pingServices();
        if (isHealthy) {
            return Result.healthy(msg);
        } else {
            return Result.unhealthy(msg);
        }
    }

    protected String pingServices() {
        isHealthy = true;
        StringBuilder healthCheckMsg = new StringBuilder();

        try {
            Employee employee = new Employee();
            employee.setId(EMPLOYEE_ID);
            employee.setName(EMPLOYEE_NAME);
            employee.setDesignation(EMPLOYEE_DESIGNATION);
            LOG.info("Request for Create or Update 'Employee' :");
            service.createOrUpdateEmployee(EMPLOYEE_ID, employee);
            healthCheckMsg.append("\n  Create or Update Employee service is " + HEALTH_CHECK_UP);
        } catch (Exception e) {
            isHealthy = false;
            healthCheckMsg.append("\n  Create or Update Employee service is " + HEALTH_CHECK_DOWN);
            LOG.error("Create or Update Employee service is not healthy : " + e.getMessage());
        }

        try {
            LOG.info("Request for Get Employee to the specified 'ID' :");
            service.getEmployeeById(EMPLOYEE_ID);
            healthCheckMsg.append("\n  Get Employee for the specified ID service is " + HEALTH_CHECK_UP);
        } catch (Exception e) {
            isHealthy = false;
            healthCheckMsg.append("\n  Get Employee for the specified ID service is " + HEALTH_CHECK_DOWN);
            LOG.error("Get Employee for the specified ID service is not healthy : " + e.getMessage());
        }

        try {
            LOG.info("Request for Get All Employees :");
            service.getAllEmployees();
            healthCheckMsg.append("Get All Employees service is " + HEALTH_CHECK_UP);
        } catch (Exception e) {
            isHealthy = false;
            healthCheckMsg.append("Get All Employees service is " + HEALTH_CHECK_DOWN);
            LOG.error("Get All Employees service is not healthy : " + e.getMessage());
        }

        try {
            LOG.info("Request for delete 'Employees' :");
            service.deleteEmployee(EMPLOYEE_ID);
            healthCheckMsg.append("\n  Delete Employees service is " + HEALTH_CHECK_UP);
        } catch (Exception e) {
            isHealthy = false;
            healthCheckMsg.append("\n  Delete Employees service is " + HEALTH_CHECK_DOWN);
            LOG.error("Delete Employees service is not healthy : " + e.getMessage());
        }

        return healthCheckMsg.toString();
    }
}
