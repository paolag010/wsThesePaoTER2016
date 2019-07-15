package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.ICompanyLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.CompanyConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.CompanyDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.DepartmentConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
@LocalBean
public class CompanyLogic implements ICompanyLogic{

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;

    public CompanyDTO createCompany(CompanyDTO company) {
        CompanyEntity entity = CompanyConverter.persistenceDTO2Entity(company);
        entityManager.persist(entity);
        return CompanyConverter.entity2PersistenceDTO(entity);
    }
    
    public int countCompanies(){
        Query count = entityManager.createQuery("select count(u) from CompanyEntity u");
        int regCount = Integer.parseInt(count.getSingleResult().toString());
        return regCount;
    }

    public List<CompanyDTO> getCompanies(Integer page, Integer maxRecords) {
        Query q = entityManager.createQuery("select u from CompanyEntity u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return CompanyConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public CompanyDTO getCompany(Long id) {
        return CompanyConverter.entity2PersistenceDTO(entityManager.find(CompanyEntity.class, id));
    }

    public void deleteCompany(Long id) {
        CompanyEntity entity = entityManager.find(CompanyEntity.class, id);
        entityManager.remove(entity);
    }

    public CompanyDTO updateCompany(CompanyDTO company) {
        CompanyEntity entity = entityManager.merge(CompanyConverter.persistenceDTO2Entity(company));
        return CompanyConverter.entity2PersistenceDTO(entity);
    }

    public CompanyDTO getMostPopulatedEmployees() {
        Query query = entityManager.createQuery("select u from CompanyEntity u WHERE size(u.ownedEmployees) =  (SELECT MAX(size(v.ownedEmployees)) from CompanyEntity v) ");        
        return CompanyConverter.entity2PersistenceDTO( (CompanyEntity)query.getResultList().get(0) );
        /*
        long l = 256;
        Long id = new Long(l);
        CompanyDTO dto = new CompanyDTO();
        dto.setId(id);
        dto.setName("Nombre de prueba");
        return dto;*/
    }
    
    public CompanyDTO getCompanyMaster(Long id) {
        return CompanyConverter.entityMaster2PersistenceDTO(entityManager.find(CompanyEntity.class, id));
    }    

    public CompanyDTO updateCompanyMaster(CompanyDTO dto) {
        CompanyEntity entity = entityManager.merge(CompanyConverter.persistenceDTO2EntityMaster(dto));
        return CompanyConverter.entityMaster2PersistenceDTO(entity);
    }

    public List<EmployeeDTO> getCompanyOwnedEmployees(Long id) {
        return EmployeeConverter.entity2PersistenceDTOList(entityManager.find(CompanyEntity.class, id).getOwnedEmployees());
    }
    
    public List<DepartmentDTO> getCompanyOwnedDepartments(Long id) {
        return DepartmentConverter.entity2PersistenceDTOList(entityManager.find(CompanyEntity.class, id).getOwnedDepartments());
    }    
}
