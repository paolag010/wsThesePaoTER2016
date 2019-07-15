package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IDepartmentLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.CompanyConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.DepartmentConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import static fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter.entity2PersistenceDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import static fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb.CompanyLogic.extractTransactionManager;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.TransactionManager;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;

@Default
@Stateless
@LocalBean
public class DepartmentLogic implements IDepartmentLogic {

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;
    protected EntityManagerFactory emf;
    protected TransactionManager transactionManager;
    
    public static TransactionManager extractTransactionManager(EntityManagerFactory factory) {
        SessionFactoryImplementor sessionFactory = factory.unwrap(SessionFactoryImplementor.class);
        return sessionFactory.getServiceRegistry().getService(JtaPlatform.class).retrieveTransactionManager();
    }
    
    public DepartmentDTO createDepartment(DepartmentDTO department) {
        System.out.println("CreateDepartment method");

        DepartmentEntity entity = DepartmentConverter.persistenceDTO2Entity(department);
        
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            CompanyEntity company = this.getSelectedCompany(department);
            if (company != null) {
                entity.setCompany(company);
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

        return DepartmentConverter.entity2PersistenceDTO(entity);
    }

    public List<DepartmentDTO> getDepartments() {
        
        System.out.println("getDepartments method");
        List<DepartmentEntity> entities = null;
        
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            Query q = entityManager.createQuery("select u from DepartmentEntity u");
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
        return DepartmentConverter.entity2PersistenceDTOList(entities);
    }

    public int countDepartments() {
            
         System.out.println("countDepartments method");
         
            String query1 = "MATCH (d:DepartmentEntity) RETURN count(d)";
            Long count = (Long) entityManager.createNativeQuery( query1).getSingleResult();
         
        return count.intValue(); 
    }

    public List<DepartmentDTO> getDepartments(Integer page, Integer maxRecords) {
        
        System.out.println("getDepartments method");
        List<DepartmentEntity> entities = null;
        
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            Query q = entityManager.createQuery("select u from DepartmentEntity u");
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
        return DepartmentConverter.entity2PersistenceDTOList(entities);
    }

    public DepartmentDTO getDepartment(Long id) {
        
        System.out.println("getDepartment method");
        DepartmentEntity entity = null;
        
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.find(DepartmentEntity.class, id);
            transactionManager.commit();

        }catch (RollbackException |NotSupportedException ex) {
            try {
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return DepartmentConverter.entity2PersistenceDTO(entity);
    }

    public void deleteDepartment(Long id) {
        
        
        System.out.println("deleteDepartment method");
        DepartmentEntity entity = null;
        
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.find(DepartmentEntity.class, id);
            if(entity != null){
                for (EmployeeEntity employee : entity.getEmployees()) {
                    employee.setDepartment(null);
                }
            }
             entity.setEmployees(new ArrayList<EmployeeEntity>());
                
            entityManager.remove(entity);
            transactionManager.commit();

        }catch (RollbackException |NotSupportedException ex) {
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

    public DepartmentDTO updateDepartment(DepartmentDTO department) {
        
        System.out.println("Update department");
        DepartmentEntity entity = null;
        
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.merge(DepartmentConverter.persistenceDTO2Entity(department));
            CompanyEntity company = this.getSelectedCompany(department);
            if (company != null) {
                entity.setCompany(company);
            }
            transactionManager.commit();

        }catch (RollbackException |NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return DepartmentConverter.entity2PersistenceDTO(entity);
    }
    
    private CompanyEntity getSelectedCompany(DepartmentDTO employee){
        System.out.println("getSelectedCompany method");
        
        if (employee != null && employee.getCompany() != null && employee.getCompany() != null) {
            return entityManager.find(CompanyEntity.class, employee.getCompany());
        }else{
            return null;
        }
    }
    
    public List<EmployeeDTO> getDepartmentEmployees(Long id) {
        System.out.println("getDepartmentEmployees method");
        return EmployeeConverter.entity2PersistenceDTOList(entityManager.find(DepartmentEntity.class, id).getEmployees());
    }
}
