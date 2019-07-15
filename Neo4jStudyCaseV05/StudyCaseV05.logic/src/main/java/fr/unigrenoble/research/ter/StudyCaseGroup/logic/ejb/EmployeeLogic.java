package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IEmployeeLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.DepartmentConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import static fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb.DepartmentLogic.extractTransactionManager;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.TransactionManager;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;

@Default
@Stateless
@LocalBean
public class EmployeeLogic implements IEmployeeLogic {

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;
    
    protected EntityManagerFactory emf;
    protected TransactionManager transactionManager;

    public static TransactionManager extractTransactionManager(EntityManagerFactory factory) {
        SessionFactoryImplementor sessionFactory = factory.unwrap(SessionFactoryImplementor.class);
        return sessionFactory.getServiceRegistry().getService(JtaPlatform.class).retrieveTransactionManager();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        
        System.out.println("createEmployee method");
        
        
        EmployeeEntity entity = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = EmployeeConverter.persistenceDTO2Entity(employee);
            CompanyEntity company = this.getSelectedCompany(employee);
            DepartmentEntity department = this.getSelectedDepartment(employee);
            if (company != null) {
                entity.setCompany(company);
            }
            if (department != null) {
                entity.setDepartment(department);
            }
            entityManager.persist(entity);         
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return EmployeeConverter.entity2PersistenceDTO(entity);
    }

    public List<EmployeeDTO> getEmployees() {
        List<EmployeeEntity> entities = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            TypedQuery<EmployeeEntity> st;
            st = entityManager.createNamedQuery( "employeeEntity.findAll", EmployeeEntity.class );
            entities = st.getResultList();
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
          
        return EmployeeConverter.entity2PersistenceDTOList(entities);
    }

    public int countEmployees() {
        
           
        String query1 = "MATCH (e:EmployeeEntity) RETURN count(e)";
        Long regCount = (Long) entityManager.createNativeQuery( query1).getSingleResult();
      
      
        return regCount.intValue();    
    }

    public List<EmployeeDTO> getEmployees(Integer page, Integer maxRecords) {
        
        
        System.out.println("getEmployee method");
        
        List<EmployeeEntity> entities=null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            TypedQuery<EmployeeEntity> q;
            q = entityManager.createNamedQuery( "employeeEntity.findAll", EmployeeEntity.class );        


            if (page != null && maxRecords != null) {
                q.setFirstResult((page - 1) * maxRecords);
                q.setMaxResults(maxRecords);
            }     
            
            entities = q.getResultList();
            
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return EmployeeConverter.entity2PersistenceDTOList(entities);
    }

    public EmployeeDTO getEmployee(Long id) {
        
        System.out.println("getEmployee method");
        EmployeeEntity entity = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.find(EmployeeEntity.class, id);
            
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
     
        return EmployeeConverter.entity2PersistenceDTO(entity);
    }

    public void deleteEmployee(Long id) {
                
        System.out.println("deleteEmployee method");
        
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            EmployeeEntity entity = entityManager.find(EmployeeEntity.class, id);
            entityManager.remove(entity);
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        
        System.out.println("updateEmployee method");
        
        EmployeeEntity entity = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.merge(EmployeeConverter.persistenceDTO2Entity(employee));
            CompanyEntity company = this.getSelectedCompany(employee);
            DepartmentEntity department = this.getSelectedDepartment(employee);
            if (company != null) {
                entity.setCompany(company);
            }
            if (department != null) {
                entity.setDepartment(department);
            }        
            transactionManager.commit();

        } catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
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
