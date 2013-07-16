package com.cybage.demo.core.dao;

import com.cybage.demo.representations.Employee;
import com.cybage.demo.wrapper.EmployeeWrapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EmployeeDao {

    public static final String EMPLOYEE_ID = "id";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_DESIGNATION = "designation";
    public static final String EMPLOYEE_TABLE = "employees";

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(String id);

    public EmployeeWrapper createOrUpdateEmployee(Employee employee);

    public void deleteEmployee(String id);

}
