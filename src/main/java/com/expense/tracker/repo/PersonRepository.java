package com.expense.tracker.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.expense.tracker.model.PersonData;

import jakarta.transaction.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<PersonData, Long> {
    Optional<PersonData> findByUsername(String username);
    @Transactional
    @Modifying
    void deleteByEmail(String email);
}
