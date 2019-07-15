package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/* Imports for eclipselink nosql */
import org.eclipse.persistence.nosql.annotations.NoSql;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;

@Entity
@NoSql(dataFormat = DataFormatType.MAPPED)
public class DepartmentEntity implements Serializable {

    @Id
//    @GeneratedValue(generator = "Department")
    @Field(name="id")
    private Long id;
    @Field(name="name")
    private String name;

    @ManyToOne
    @JoinField
    private CompanyEntity company;
  
    @OneToMany(cascade = { CascadeType.MERGE,CascadeType.REFRESH })
    @JoinField
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
