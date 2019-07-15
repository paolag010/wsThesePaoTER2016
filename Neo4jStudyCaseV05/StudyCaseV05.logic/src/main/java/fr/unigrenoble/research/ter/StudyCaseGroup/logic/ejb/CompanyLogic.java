package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.ICompanyLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.CompanyConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.CompanyDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.DepartmentConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;

@Stateless 
@LocalBean
public class CompanyLogic implements ICompanyLogic{

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;
    protected EntityManagerFactory emf;
    protected TransactionManager transactionManager;
    
    public static TransactionManager extractTransactionManager(EntityManagerFactory factory) {
        SessionFactoryImplementor sessionFactory = factory.unwrap(SessionFactoryImplementor.class);
        return sessionFactory.getServiceRegistry().getService(JtaPlatform.class).retrieveTransactionManager();
    }

      
    @SuppressWarnings("UseSpecificCatch")
    public  CompanyDTO createCompany(CompanyDTO company) {
        
        System.out.println("Create company method");
        CompanyEntity entity = CompanyConverter.persistenceDTO2Entity(company);
        
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entityManager.persist(entity);            
            transactionManager.commit();

        } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } 
        catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return CompanyConverter.entity2PersistenceDTO(entity);
    }
    
    public  int countCompanies(){
        
        System.out.println("count companies method");
      
        String query1 = "MATCH (a:CompanyEntity) RETURN count(a)";
        Long regCount = (Long) entityManager.createNativeQuery( query1).getSingleResult();
        
     
        return regCount.intValue();
        
       
    }

    public  List<CompanyDTO> getCompanies(Integer page, Integer maxRecords) {
        
        System.out.println("getCompanies method");
        
        List<CompanyEntity> entities = null;
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
           
            Query q = entityManager.createQuery("select u from CompanyEntity u");
            if (page != null && maxRecords != null) {
                q.setFirstResult((page - 1) * maxRecords);
                q.setMaxResults(maxRecords);
            }
            entities = q.getResultList();
            transactionManager.commit();
         } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        }  catch (Exception ex) {
           
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         return CompanyConverter.entity2PersistenceDTOList(entities);
    }

    public  CompanyDTO getCompany(Long id) {
        
        System.out.println("getCompany method");
        
        CompanyEntity company = null;
        
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            
            company = entityManager.find(CompanyEntity.class, id);
            
            transactionManager.commit();
        
        } catch (RollbackException | NotSupportedException ex) {
            try {
               
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
         return CompanyConverter.entity2PersistenceDTO(company);
    }

    public  void deleteCompany(Long id) {
        
        System.out.println("deleteCompany method");
      
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            CompanyEntity entity = entityManager.find(CompanyEntity.class, id);
            entityManager.remove(entity);
            transactionManager.commit();
           
        } catch (RollbackException | NotSupportedException ex) {
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

    public  CompanyDTO updateCompany(CompanyDTO company) {

        System.out.println("updateCompany method");
        
        CompanyEntity entity = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            entity = entityManager.merge(CompanyConverter.persistenceDTO2Entity(company));
            transactionManager.commit();
           
         } catch (RollbackException | NotSupportedException ex) {
        try {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
            transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return CompanyConverter.entity2PersistenceDTO(entity);
    }

    public  CompanyDTO getMostPopulatedEmployees() {
        
        System.out.println("getMostPopulatedEmployees method");
        
        
        CompanyEntity entity = null;
        try {
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
            String query= "match (m)-[]->(n:CompanyEntity) with  n, count(*) as numberofEmployees order by numberofEmployees desc return n limit 1";
            Query q = entityManager.createNativeQuery(query, CompanyEntity.class);
            
            entity = (CompanyEntity)q.getSingleResult();
             transactionManager.commit();
            
         } catch (RollbackException | NotSupportedException ex) {
        try {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
            transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return CompanyConverter.entity2PersistenceDTO(entity);
   
    }
    
    public  CompanyDTO getCompanyMaster(Long id) {
        
        System.out.println("getCompanyMaster method");
        CompanyEntity entity = null;
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
           
            transactionManager.begin();
           
            entity=entityManager.find(CompanyEntity.class, id);
                      
        
            transactionManager.commit();

        } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return CompanyConverter.entityMaster2PersistenceDTO(entity);
    }    

    public  CompanyDTO updateCompanyMaster(CompanyDTO dto) {
        
        System.out.println("updateCompanyMaster method");
        CompanyEntity entity = null;
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
           
            transactionManager.begin();
           
            entity = entityManager.merge(CompanyConverter.persistenceDTO2EntityMaster(dto));   
            
            transactionManager.commit();
           
        } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
   
        return CompanyConverter.entityMaster2PersistenceDTO(entity);
    }

    public  List<EmployeeDTO> getCompanyOwnedEmployees(Long id) {
        
        System.out.println("getCompanyOwnedEmployees method");
        
        List<EmployeeEntity> employees = null;
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
           
            transactionManager.begin();
           
            employees = entityManager.find(CompanyEntity.class, id).getOwnedEmployees();    
            transactionManager.commit();
            

        } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            } 
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
   
        return EmployeeConverter.entity2PersistenceDTOList(employees);
    }
    
    public  List<DepartmentDTO> getCompanyOwnedDepartments(Long id) {
        
        System.out.println("getCompanyOwnedDepartments method");
        
        List<DepartmentEntity> departments = null;
        try {
            
            emf = entityManager.getEntityManagerFactory();
            transactionManager = extractTransactionManager(emf);
            transactionManager.begin();
           
            departments = entityManager.find(CompanyEntity.class, id).getOwnedDepartments();   
            transactionManager.commit();
            

        } catch (RollbackException | NotSupportedException ex) {
            try {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
                transactionManager.rollback();
            } catch (Exception ex1) {
                Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            Logger.getLogger(CompanyLogic.class.getName()).log(Level.SEVERE, null, ex);
        } 
   
        
        
        return DepartmentConverter.entity2PersistenceDTOList(departments);
    }    
}
