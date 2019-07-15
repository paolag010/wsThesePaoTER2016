package fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.EmployeeDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.EmployeeEntity;
import java.util.ArrayList;
import java.util.List;

public class EmployeeConverter {

    public static EmployeeDTO entity2PersistenceDTO(EmployeeEntity entity) {
        if (entity != null) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSalary(entity.getSalary());
            dto.setAddress(entity.getAddress());
            if (entity.getCompany() != null) {
                dto.setCompany(entity.getCompany().getId());
            }
            if (entity.getDepartment() != null) {
                dto.setDepartment(entity.getDepartment().getId());
            }
            return dto;
        } else {
            return null;
        }
    }

    public static EmployeeEntity persistenceDTO2Entity(EmployeeDTO dto) {
        if (dto != null) {
            EmployeeEntity entity = new EmployeeEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setSalary(dto.getSalary());
            entity.setAddress(dto.getAddress());
            
            if (dto.getCompany()!= null) {
                CompanyEntity company = new CompanyEntity();
                company.setId(dto.getCompany());
                entity.setCompany(company);
            }

            if (dto.getDepartment()!= null) {
                DepartmentEntity department = new DepartmentEntity();
                department.setId(dto.getDepartment());
                entity.setDepartment(department);
            }
            return entity;
        } else {
            return null;
        }
    }

    public static List<EmployeeDTO> entity2PersistenceDTOList(List<EmployeeEntity> entities) {
        List<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
        if (entities != null) {
            for (EmployeeEntity entity : entities) {
                dtos.add(entity2PersistenceDTO(entity));
            }
        }
        return dtos;
    }

    public static List<EmployeeEntity> persistenceDTO2EntityList(List<EmployeeDTO> dtos) {
        List<EmployeeEntity> entities = new ArrayList<EmployeeEntity>();
        if (dtos != null) {
            for (EmployeeDTO dto : dtos) {
                entities.add(persistenceDTO2Entity(dto));
            }
        }
        return entities;
    }
}
