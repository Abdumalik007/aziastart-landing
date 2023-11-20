package com.azia.landing.repository;

import com.azia.landing.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("""
    select s from Student s where (:second is null and (s.firstName ilike :first% or s.lastName ilike :first%))
    or (s.firstName ilike :first% and s.lastName ilike :second%)
    """)
    List<Student> findByFirstNameOrLastName(String first, String second);
}

