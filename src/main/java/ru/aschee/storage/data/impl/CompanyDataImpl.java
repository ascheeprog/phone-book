package ru.aschee.storage.data.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aschee.domain.Company;
import ru.aschee.storage.Storage;
import ru.aschee.storage.data.CompanyData;
import ru.aschee.storage.repository.CompanyRepository;

@Primary
@Component
public class CompanyDataImpl extends Storage implements CompanyData {

    private final CompanyRepository repository;

    public CompanyDataImpl(CompanyRepository repository) {
        super(Company.class);
        this.repository = repository;
    }

    @Override
    @Transactional
    public Company putCompany(Company company) {
        return repository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Company findCompanyById(String companyId) {
        return repository.findById(companyId).orElse(null);
    }
}
