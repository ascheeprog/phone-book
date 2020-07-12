package ru.aschee.storage.data;

import ru.aschee.domain.Company;

public interface CompanyData {
    Company putCompany(Company company);

    Company findCompanyById(String companyId);
}