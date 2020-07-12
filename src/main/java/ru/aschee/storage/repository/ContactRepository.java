package ru.aschee.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.aschee.domain.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>, QuerydslPredicateExecutor<Contact> {
}
