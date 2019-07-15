package fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/* Imports for eclipselink nosql */
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;

@Entity
@NoSql(dataFormat = DataFormatType.MAPPED) 
public class CompanyEntity implements Serializable {
    
    @Id
    @Field(name="id")
    private Long id;
    
    @Field(name="name")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinField
    private List<EmployeeEntity> ownedEmployees;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinField
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
