package com.cybage.demo.core;

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
public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(String id);

    public EmployeeWrapper createOrUpdateEmployee(String id, Employee employee);

    public void deleteEmployee(String id);
}
