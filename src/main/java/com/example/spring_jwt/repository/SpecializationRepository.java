package com.example.spring_jwt.repository;

import com.example.spring_jwt.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    List<Specialization> getAllBySpecName(String specName);
}
