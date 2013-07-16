package com.cybage.demo.core;

import com.cybage.demo.core.dao.EmployeeDao;
import com.cybage.demo.core.exception.ErrorException;
import com.cybage.demo.core.exception.ResourceNotFound;
import com.cybage.demo.representations.Employee;
import com.cybage.demo.wrapper.EmployeeWrapper;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Service class to implement business logic
 */
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDao authDao;

    public EmployeeServiceImpl(EmployeeDao authDao) {
        this.authDao = Preconditions.checkNotNull(authDao);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return authDao.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(String id) {

        Employee employee = authDao.getEmployeeById(id);
        if (employee == null) {
            LOG.info("Requested Resource for id '{}' could not be found.", id);
            throw new ResourceNotFound(ResourceNotFound.MESSAGE);
        }

        return employee;
    }

    @Override
    public EmployeeWrapper createOrUpdateEmployee(String id, Employee employee) {
        if(id==null || id.equalsIgnoreCase(employee.getId())) {
            LOG.info("Id provided in URI {} is mismatched with Id {} provided in Payload.", id, employee.getId());
            throw new ErrorException(400, "Id provided in URI is mismatched with Id provided in Payload");
        }
        return authDao.createOrUpdateEmployee(employee);
    }


    @Override
    public void deleteEmployee(String id) {

        authDao.deleteEmployee(id);
    }
}
