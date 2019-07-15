/* ========================================================================
 * ========================================================================
 */

package fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeDTO {

    private Long id;

    private String name;

    private Integer salary;

    private String address;
    
    private Long company;
    
    private Long department;

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

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }
    
}

