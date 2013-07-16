package com.cybage.demo.resource;

import com.cybage.demo.core.EmployeeService;
import com.cybage.demo.core.exception.ErrorException;
import com.cybage.demo.representations.Employee;
import com.cybage.demo.representations.ErrorMessage;
import com.cybage.demo.util.Constants;
import com.cybage.demo.wrapper.EmployeeWrapper;
import com.google.common.base.Preconditions;
import com.yammer.metrics.annotation.Timed;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class is the controller for the this API.
 */
@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class EmployeeResource {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeResource.class);

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = Preconditions.checkNotNull(employeeService);
    }

    /**
     * This method is to get All Employees from DB.
     * @return
     */
    @GET
    @Timed
    @Path("/employees")
    public Response getAllEmployees() {

        List<Employee> employeeList;
        try {
            employeeList = employeeService.getAllEmployees();
        } catch (ErrorException e) {
            return errorResponse(e);
        }
        return Response.ok(employeeList).build();
    }

    /**
     * This method is to get particular employee based on ID
     * @param id
     * @return
     */
    @GET
    @Timed
    @Path("/employees/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getEmployeeById(@PathParam(Constants.PATH_PARAM_EMPLOYEE_ID) String id) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get employee request with id :", id);
        }

        if (StringUtils.isEmpty(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.MISSING_PATH_PARAM_MSG).build();
        }

        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (ErrorException e) {
            return errorResponse(e);
        }

        return Response.ok(employee).build();
    }

    /**
     * This method is to create a new employee based on provided id
     * @param id
     * @param employee
     * @return
     */
    @PUT
    @Timed
    @Path("/employees/{id}")
    public Response createOrUpdateEmployee(@PathParam(Constants.PATH_PARAM_EMPLOYEE_ID) String id, Employee employee) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Create or Update Employee request with id '{}' and Api-Users '{}' ", id, employee);
        }

        EmployeeWrapper employeeWrapper;
        try {
            employeeWrapper = employeeService.createOrUpdateEmployee(id, employee);
        } catch (ErrorException e) {
            return errorResponse(e);
        }

        return Response.status(Response.Status.CREATED).entity(employeeWrapper).build();
    }

    /**
     * This method is to delete a particular record based on provided ID.
     * @param id
     * @return
     */
    @DELETE
    @Timed
    @Path("/employees/{id}")
    public Response deleteEmployee(@PathParam(Constants.PATH_PARAM_EMPLOYEE_ID) String id) {
        try {
            employeeService.deleteEmployee(id);
        } catch (ErrorException e) {
            return errorResponse(e);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * Build error response
     *
     * @param e Specifies exception
     * @return Appropriate Response code
     */
    private Response errorResponse(ErrorException e) {
        final ErrorMessage error = new ErrorMessage(e.getMessage());
        return Response.status(e.getStatus()).entity(error).type(MediaType.APPLICATION_JSON).build();
    }

}
