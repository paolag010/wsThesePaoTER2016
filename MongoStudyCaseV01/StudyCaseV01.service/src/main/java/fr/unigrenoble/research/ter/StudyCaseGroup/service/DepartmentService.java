/* ========================================================================
  * ========================================================================
 */

package fr.unigrenoble.research.ter.StudyCaseGroup.service;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IDepartmentLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
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

@Path("/departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService {

    @Inject private IDepartmentLogic departmentLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    @POST
    public DepartmentDTO createDepartment(DepartmentDTO deparment) {
        DepartmentDTO dto = departmentLogic.createDepartment(deparment);
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
    public void deleteDepartment(@PathParam("id") Long id) {
        departmentLogic.deleteDepartment(id);
    }

    @GET
    public List<DepartmentDTO> getDepartments() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", departmentLogic.countDepartments());
        }
        return departmentLogic.getDepartments(page, maxRecords);
    }

    @GET
    @Path("{id}")
    public DepartmentDTO getDepartment(@PathParam("id") Long id) {
        return departmentLogic.getDepartment(id);
    }

    @PUT
    @Path("{id}")
    public DepartmentDTO updateDepartment(@PathParam("id") Long id, DepartmentDTO department) {
        return departmentLogic.updateDepartment(department);
    }
    
    @GET
    @Path("{id}/employees")
    public List<EmployeeDTO> getDepartmentEmployees(@PathParam("id") Long id) {
        return departmentLogic.getDepartmentEmployees(id);
    }    

}
