package com.azia.landing.repository;

import com.azia.landing.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("""
    select t from Teacher t where (:second is null and (t.firstName ilike :first% or t.lastName ilike :first%))
    or (t.firstName ilike :first% and t.lastName ilike :second%)
    """)
    List<Teacher> findByFirstNameOrLastName(String first, String second);
}
