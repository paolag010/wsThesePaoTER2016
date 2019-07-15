/* ========================================================================
  * ========================================================================
 */

package fr.unigrenoble.research.ter.StudyCaseGroup.service;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IEmployeeLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeService {

    @Inject private IEmployeeLogic employeeLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    @POST
    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        EmployeeDTO dto = employeeLogic.createEmployee(employee);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }

    @DELETE
    @Path("{id}")
    public void deleteEmployee(@PathParam("id") Long id) {
        employeeLogic.deleteEmployee(id);
    }

    @GET
    public List<EmployeeDTO> getEmployees() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", employeeLogic.countEmployees());
        }
        return employeeLogic.getEmployees(page, maxRecords);
    }

    @GET
    @Path("{id}")
    public EmployeeDTO getEmployee(@PathParam("id") Long id) {
        return employeeLogic.getEmployee(id);
    }

    @PUT
    @Path("{id}")
    public EmployeeDTO updateEmployee(@PathParam("id") Long id, EmployeeDTO employee) {
        return employeeLogic.updateEmployee(employee);
    }

}
