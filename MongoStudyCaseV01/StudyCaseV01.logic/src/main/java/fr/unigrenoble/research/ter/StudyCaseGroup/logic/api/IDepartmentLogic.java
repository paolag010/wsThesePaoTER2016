/* ========================================================================
  * ========================================================================
  */
package fr.unigrenoble.research.ter.StudyCaseGroup.logic.api;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import java.util.List;

public interface IDepartmentLogic {

    public DepartmentDTO createDepartment(DepartmentDTO detail);
    
    public int countDepartments();

    public List<DepartmentDTO> getDepartments(Integer page, Integer maxRecords);

    public DepartmentDTO getDepartment(Long id);

    public void deleteDepartment(Long id);

    public DepartmentDTO updateDepartment(DepartmentDTO department);
    
    public List<EmployeeDTO> getDepartmentEmployees(Long id);
}
