package ru.aschee.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.aschee.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>, QuerydslPredicateExecutor<Company> {
}
