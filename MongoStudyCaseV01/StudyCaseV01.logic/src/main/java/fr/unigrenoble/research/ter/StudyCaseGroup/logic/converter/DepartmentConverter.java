package fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.DepartmentDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.DepartmentEntity;
import java.util.ArrayList;
import java.util.List;

public class DepartmentConverter {

    public static DepartmentDTO entity2PersistenceDTO(DepartmentEntity entity) {
        if (entity != null) {
            DepartmentDTO dto = new DepartmentDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            if (entity.getCompany() != null) {
                dto.setCompany(entity.getCompany().getId());
            }
            dto.setEmployees(EmployeeConverter.entity2PersistenceDTOList(entity.getEmployees()));
            return dto;
        } else {
            return null;
        }
    }

    public static DepartmentEntity persistenceDTO2Entity(DepartmentDTO dto) {
        if (dto != null) {
            DepartmentEntity entity = new DepartmentEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            
            if (dto.getCompany()!= null) {
                CompanyEntity company = new CompanyEntity();
                company.setId(dto.getCompany());
                entity.setCompany(company);
            }
            
            entity.setEmployees(EmployeeConverter.persistenceDTO2EntityList(dto.getEmployees()));

            return entity;
        } else {
            return null;
        }
    }

    public static List<DepartmentDTO> entity2PersistenceDTOList(List<DepartmentEntity> entities) {
        List<DepartmentDTO> dtos = new ArrayList<DepartmentDTO>();
        if (entities != null) {
            for (DepartmentEntity entity : entities) {
                dtos.add(entity2PersistenceDTO(entity));
            }
        }
        return dtos;
    }

    public static List<DepartmentEntity> persistenceDTO2EntityList(List<DepartmentDTO> dtos) {
        List<DepartmentEntity> entities = new ArrayList<DepartmentEntity>();
        if (dtos != null) {
            for (DepartmentDTO dto : dtos) {
                entities.add(persistenceDTO2Entity(dto));
            }
        }
        return entities;
    }
}
