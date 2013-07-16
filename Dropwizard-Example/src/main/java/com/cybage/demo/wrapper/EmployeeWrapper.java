package com.cybage.demo.wrapper;

import com.cybage.demo.representations.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * The point of this class is to wrap an Employee object with
 * additional information to be used at the service layer.
 */
public class EmployeeWrapper {

    @JsonProperty
    private Boolean existingUser = false;

    @JsonProperty
    private Employee employee;

    public EmployeeWrapper(boolean existingUser, Employee employee) {
        this.existingUser = existingUser;
        this.employee = employee;
    }

    public Boolean getExistingUser() {
        return existingUser;
    }

    public void setExistingUser(Boolean existingUser) {
        this.existingUser = existingUser;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
