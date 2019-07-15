package fr.unigrenoble.research.ter.StudyCaseGroup.logic.ejb;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.AutoKeyGenerate.LongIdGenerator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.api.IDepartmentLogic;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.DepartmentConverter;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter;
import static fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter.EmployeeConverter.entity2PersistenceDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Default
@Stateless
@LocalBean
public class DepartmentLogic implements IDepartmentLogic {

    @PersistenceContext(unitName = "StudyCasePU")
    protected EntityManager entityManager;

    public DepartmentDTO createDepartment(DepartmentDTO department) {

        /* Adding id */
        LongIdGenerator key = new LongIdGenerator();
        department.setId(key.autoKey());

        DepartmentEntity entity = DepartmentConverter.persistenceDTO2Entity(department);
        CompanyEntity company = this.getSelectedCompany(department);
        if (company != null) {
            entity.setCompany(company);
        }
        entityManager.persist(entity);
        return DepartmentConverter.entity2PersistenceDTO(entity);
    }

    public List<DepartmentDTO> getDepartments() {
        Query q = entityManager.createQuery("select u from DepartmentEntity u");
        return DepartmentConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public int countDepartments() {
        // Query count = entityManager.createQuery("select count(u) from DepartmentEntity u");
        //return Integer.parseInt(count.getSingleResult().toString());
        
        Query q = entityManager.createQuery("select u from DepartmentEntity u");
        int regCount = q.getResultList().size();

        return regCount;
    }

    public List<DepartmentDTO> getDepartments(Integer page, Integer maxRecords) {
        Query q = entityManager.createQuery("select u from DepartmentEntity u");
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return DepartmentConverter.entity2PersistenceDTOList(q.getResultList());
    }

    public DepartmentDTO getDepartment(Long id) {
        return DepartmentConverter.entity2PersistenceDTO(entityManager.find(DepartmentEntity.class, id));
    }

    public void deleteDepartment(Long id) {
        DepartmentEntity entity = entityManager.find(DepartmentEntity.class, id);

        if (entity != null) {
            for (EmployeeEntity employee : entity.getEmployees()) {
                employee.setDepartment(null);
            }
        }

        entity.setEmployees(new ArrayList<EmployeeEntity>());

        entityManager.remove(entity);
    }

    public DepartmentDTO updateDepartment(DepartmentDTO department) {
        DepartmentEntity entity = entityManager.merge(DepartmentConverter.persistenceDTO2Entity(department));
        CompanyEntity company = this.getSelectedCompany(department);
        if (company != null) {
            entity.setCompany(company);
        }
        return DepartmentConverter.entity2PersistenceDTO(entity);
    }

    private CompanyEntity getSelectedCompany(DepartmentDTO employee) {
        if (employee != null && employee.getCompany() != null && employee.getCompany() != null) {
            return entityManager.find(CompanyEntity.class, employee.getCompany());
        } else {
            return null;
        }
    }

    public List<EmployeeDTO> getDepartmentEmployees(Long id) {
        return EmployeeConverter.entity2PersistenceDTOList(entityManager.find(DepartmentEntity.class, id).getEmployees());
    }
}
