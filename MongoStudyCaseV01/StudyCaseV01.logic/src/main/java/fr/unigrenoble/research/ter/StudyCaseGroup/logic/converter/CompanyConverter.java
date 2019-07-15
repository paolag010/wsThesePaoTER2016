package fr.unigrenoble.research.ter.StudyCaseGroup.logic.converter;

import fr.unigrenoble.research.ter.StudyCaseGroup.logic.dto.CompanyDTO;
import fr.unigrenoble.research.ter.StudyCaseGroup.logic.entity.CompanyEntity;
import java.util.ArrayList;
import java.util.List;

public class CompanyConverter {

    public static CompanyDTO entity2PersistenceDTO(CompanyEntity entity) {
        if (entity != null) {
            CompanyDTO dto = new CompanyDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        } else {
            return null;
        }
    }

    public static CompanyEntity persistenceDTO2Entity(CompanyDTO dto) {
        System.out.println("persistenceDTO2Entity");
        
         System.out.println("CompanyDTO >>" + dto + "\n");
        
        if (dto != null) {
            CompanyEntity entity = new CompanyEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            return entity;
        } else {
            return null;
        }
    }

   public static CompanyDTO entityMaster2PersistenceDTO(CompanyEntity entity) {
        if (entity != null) {
            CompanyDTO dto = entity2PersistenceDTO(entity);
            dto.setOwnedEmployees(EmployeeConverter.entity2PersistenceDTOList(entity.getOwnedEmployees()));
            dto.setOwnedDepartments(DepartmentConverter.entity2PersistenceDTOList(entity.getOwnedDepartments()));
            return dto;
        } else {
            return null;
        }
    }

    public static CompanyEntity persistenceDTO2EntityMaster(CompanyDTO dto) {
        if (dto != null) {
            CompanyEntity entity = persistenceDTO2Entity(dto);
            entity.setOwnedEmployees(EmployeeConverter.persistenceDTO2EntityList(dto.getOwnedEmployees()));
            entity.setOwnedDepartments(DepartmentConverter.persistenceDTO2EntityList(dto.getOwnedDepartments()));
            return entity;
        } else {
            return null;
        } 
    }

    public static List<CompanyDTO> entity2PersistenceDTOList(List<CompanyEntity> entities) {    
        List<CompanyDTO> dtos = new ArrayList<CompanyDTO>();
        if (entities != null) {
            for (CompanyEntity entity : entities) {
                dtos.add(entity2PersistenceDTO(entity));
            }
        }
        return dtos;
    }

    public static List<CompanyEntity> persistenceDTO2EntityList(List<CompanyDTO> dtos) {
        List<CompanyEntity> entities = new ArrayList<CompanyEntity>();
        if (dtos != null) {
            for (CompanyDTO dto : dtos) {
                entities.add(persistenceDTO2Entity(dto));
            }
        }
        return entities;
    }
}
