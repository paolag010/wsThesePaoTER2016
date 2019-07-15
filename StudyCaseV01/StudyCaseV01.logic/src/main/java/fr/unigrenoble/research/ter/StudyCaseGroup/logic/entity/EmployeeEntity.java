package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery( name="employeeEntity.countAll", query="select count(u) from EmployeeEntity u"),
	@NamedQuery( name="employeeEntity.findAll", query="select u from EmployeeEntity u")
})
public class EmployeeEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Employee")
    private Long id;
    private String name;
    private Integer salary;
    private String address;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private CompanyEntity company;
    
    @ManyToOne
    @JoinColumn(nullable=true)
    private DepartmentEntity department;    

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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }
}
