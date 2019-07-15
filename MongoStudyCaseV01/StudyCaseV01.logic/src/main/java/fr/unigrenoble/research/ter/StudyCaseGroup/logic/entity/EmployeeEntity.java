package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/* Imports for eclipselink nosql */
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
@NamedQueries({
        @NamedQuery( name="employeeEntity.countAll", query="select count(u) from EmployeeEntity u"),
	@NamedQuery( name="employeeEntity.findAll", query="select u from EmployeeEntity u")
})
public class EmployeeEntity implements Serializable {

    @Id
//    @GeneratedValue(generator = "Employee")
    @Field(name = "id")
    private Long id;
    @Field(name = "name")
    private String name;
    @Field(name = "salary")
    private Integer salary;
    @Field(name = "address")
    private String address;
    
    @ManyToOne
    @JoinField
    private CompanyEntity company;
    
    @ManyToOne
    @JoinField
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
