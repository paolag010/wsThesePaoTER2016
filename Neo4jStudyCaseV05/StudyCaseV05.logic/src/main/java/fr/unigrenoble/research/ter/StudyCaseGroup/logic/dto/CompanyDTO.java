package fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyDTO {

    private Long id;

    private String name;

    private List<EmployeeDTO> ownedEmployees;
    
    private List<DepartmentDTO> ownedDepartments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeDTO> getOwnedEmployees() {
        return ownedEmployees;
    }

    public void setOwnedEmployees(List<EmployeeDTO> ownedEmployees) {
        this.ownedEmployees = ownedEmployees;
    }

    public List<DepartmentDTO> getOwnedDepartments() {
        return ownedDepartments;
    }

    public void setOwnedDepartments(List<DepartmentDTO> ownedDepartments) {
        this.ownedDepartments = ownedDepartments;
    }
}
