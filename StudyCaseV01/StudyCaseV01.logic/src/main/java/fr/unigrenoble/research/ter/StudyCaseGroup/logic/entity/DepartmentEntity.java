package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.JoinFetch;

@Entity
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Department")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(nullable=false)
    private CompanyEntity company;
    
    @OneToMany(mappedBy = "department", cascade = { CascadeType.MERGE,CascadeType.REFRESH })    
    private List<EmployeeEntity> employees;    

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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }
}
