package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.AutoKeyGenerate.LongIdGenerator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IEmployeeLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Default
@Stateless
@LocalBean
public class EmployeeLogic implements IEmployeeLogic {

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        
         /* Adding id */
        LongIdGenerator key = new LongIdGenerator();
        employee.setId(key.autoKey());
        
        EmployeeEntity entity = EmployeeConverter.persistenceDTO2Entity(employee);
        CompanyEntity company = this.getSelectedCompany(employee);
        DepartmentEntity department = this.getSelectedDepartment(employee);
        if (company != null) {
            entity.setCompany(company);
        }
        if (department != null) {
            entity.setDepartment(department);
        }
        entityManager.persist(entity);
        return EmployeeConverter.entity2PersistenceDTO(entity);
    }

    public List<EmployeeDTO> getEmployees() {
        //Query q = entityManager.createQuery("select u from EmployeeEntity u");
        //return EmployeeConverter.entity2PersistenceDTOList(q.getResultList());
        TypedQuery<EmployeeEntity> st;
        st = entityManager.createNamedQuery( "employeeEntity.findAll", EmployeeEntity.class );        
        return EmployeeConverter.entity2PersistenceDTOList(st.getResultList());
    }

    public int countEmployees() {
        //Query count = entityManager.createQuery("select count(u) from EmployeeEntity u");
        //return Integer.parseInt(count.getSingleResult().toString());
        
//        TypedQuery<EmployeeEntity> st;
//        st = entityManager.createNamedQuery( "employeeEntity.countAll", EmployeeEntity.class );        
//        return Integer.parseInt(st.getSingleResult().toString());
        
         TypedQuery<EmployeeEntity> q;
        q = entityManager.createNamedQuery( "employeeEntity.findAll", EmployeeEntity.class );  
        int count = q.getResultList().size();
        
        return count;
    }

    public List<EmployeeDTO> getEmployees(Integer page, Integer maxRecords) {
        //Query q = entityManager.createQuery("select u from EmployeeEntity u");
        TypedQuery<EmployeeEntity> q;
        q = entityManager.createNamedQuery( "employeeEntity.findAll", EmployeeEntity.class );        
        
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return EmployeeConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public EmployeeDTO getEmployee(Long id) {
        return EmployeeConverter.entity2PersistenceDTO(entityManager.find(EmployeeEntity.class, id));
    }

    public void deleteEmployee(Long id) {
        EmployeeEntity entity = entityManager.find(EmployeeEntity.class, id);
        entityManager.remove(entity);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        EmployeeEntity entity = entityManager.merge(EmployeeConverter.persistenceDTO2Entity(employee));
        CompanyEntity company = this.getSelectedCompany(employee);
        DepartmentEntity department = this.getSelectedDepartment(employee);
        if (company != null) {
            entity.setCompany(company);
        }
        if (department != null) {
            entity.setDepartment(department);
        }        
        return EmployeeConverter.entity2PersistenceDTO(entity);
    }
    
    private CompanyEntity getSelectedCompany(EmployeeDTO employee){
        if (employee != null && employee.getCompany() != null && employee.getCompany() != null) {
            return entityManager.find(CompanyEntity.class, employee.getCompany());
        }else{
            return null;
        }
    }
    
    private DepartmentEntity getSelectedDepartment(EmployeeDTO employee){
        if (employee != null && employee.getDepartment() != null && employee.getDepartment() != null) {
            return entityManager.find(DepartmentEntity.class, employee.getDepartment());
        }else{
            return null;
        }
    }
    
}
