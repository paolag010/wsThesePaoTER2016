package fr.unigrenoble.research.ter.StudyCaseGroup.service;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.ICompanyLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.CompanyDTO;
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

@Path("/companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyService {

    @Inject private ICompanyLogic companyLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("maxRecords") private Integer maxRecords;

    @POST
    public CompanyDTO createCompany(CompanyDTO company) {
        CompanyDTO dto = companyLogic.createCompany(company);
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
    public void deleteCompany(@PathParam("id") Long id) {
        companyLogic.deleteCompany(id);
    }

    @GET
    public List<CompanyDTO> getCompanies() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", companyLogic.countCompanies());
        }
        return companyLogic.getCompanies(page, maxRecords);
    }

    @GET
    @Path("{id}")
    public CompanyDTO getCompany(@PathParam("id") Long id) {
        return companyLogic.getCompany(id);
    }

    @PUT
    @Path("{id}")
    public CompanyDTO updateCompany(@PathParam("id") Long id, CompanyDTO dto) {
        dto.setId(id);
        return companyLogic.updateCompany(dto);
    }

    @GET
    @Path("mostPopulatedEmployees")
    public CompanyDTO getMostPopulatedEmployees() {
        return companyLogic.getMostPopulatedEmployees();
    }
    
    @GET
    @Path("{id}/ownedEmployees")
    public List<EmployeeDTO> getCompanyOwnedEmployees(@PathParam("id") Long id) {
        return companyLogic.getCompanyOwnedEmployees(id);
    }
    
    @GET
    @Path("{id}/ownedDepartments")
    public List<DepartmentDTO> getCompanyOwnedDepartments(@PathParam("id") Long id) {
        return companyLogic.getCompanyOwnedDepartments(id);
    }    
    
    public ICompanyLogic getcompanyLogic(){
        return companyLogic;
    }
    
    public void setcompanyLogic(ICompanyLogic companyLogic){
        this.companyLogic = companyLogic;
    }
            
}
