package ru.aschee.mapper;

import org.mapstruct.Mapper;
import ru.aschee.domain.Company;
import ru.aschee.dto.CompanyDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company dtoToCompany(CompanyDTO dto);

    CompanyDTO companyToDTO(Company company);
}
