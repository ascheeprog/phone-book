package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.domain.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findByPhoneAndUserId(String numberPhone,Integer userId);
    List<Contact> findByUserId(Integer id);
    Optional<Contact> findByIdAndUserId(Integer contactId, Integer userId);
}
