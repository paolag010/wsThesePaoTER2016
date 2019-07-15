package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.JoinFetch;

@Entity
public class CompanyEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Company")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinFetch
    private List<EmployeeEntity> ownedEmployees;
    
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinFetch
    private List<DepartmentEntity> ownedDepartments;

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

    public List<EmployeeEntity> getOwnedEmployees() {
        return ownedEmployees;
    }

    public void setOwnedEmployees(List<EmployeeEntity> ownedEmployees) {
        this.ownedEmployees = ownedEmployees;
    }

    public List<DepartmentEntity> getOwnedDepartments() {
        return ownedDepartments;
    }

    public void setOwnedDepartments(List<DepartmentEntity> ownedDepartments) {
        this.ownedDepartments = ownedDepartments;
    }    
}
