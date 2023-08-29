package com.azia.landing.repository;

import com.azia.landing.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsStudentByIdAndImageIsNull(Integer id);
    @Query("select s.image.name from Student s where s.id = :id")
    String getStudentImageName(Integer id);

}

