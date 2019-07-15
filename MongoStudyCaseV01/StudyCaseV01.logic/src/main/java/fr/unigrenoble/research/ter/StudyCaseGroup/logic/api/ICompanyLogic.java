package fr.unigrenoble.research.ter.StudyCaseGroup.logic.api;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.CompanyDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import java.util.List;


public interface ICompanyLogic {
    
    public int countCompanies();

    public CompanyDTO createCompany(CompanyDTO detail);

    public List<CompanyDTO> getCompanies(Integer page, Integer maxRecords);

    public CompanyDTO getCompany(Long id);

    public void deleteCompany(Long id);

    public CompanyDTO updateCompany(CompanyDTO detail);
    
    public CompanyDTO getMostPopulatedEmployees();
    
    public CompanyDTO getCompanyMaster(Long id);
    
    public CompanyDTO updateCompanyMaster(CompanyDTO dto);
    
    public List<EmployeeDTO> getCompanyOwnedEmployees(Long id);
    
    public List<DepartmentDTO> getCompanyOwnedDepartments(Long id);
}
