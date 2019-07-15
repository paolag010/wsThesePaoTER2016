/* ========================================================================
  * ========================================================================
  */
package fr.unigrenoble.research.ter.StudyCaseGroup.logic.api;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import java.util.List;

public interface IEmployeeLogic {

    public EmployeeDTO createEmployee(EmployeeDTO detail);
    
    public int countEmployees();

    public List<EmployeeDTO> getEmployees(Integer page, Integer maxRecords);

    public EmployeeDTO getEmployee(Long id);

    public void deleteEmployee(Long id);

    public EmployeeDTO updateEmployee(EmployeeDTO detail);
}
