package com.azia.landing.repository;

import com.azia.landing.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    boolean existsTeacherByIdAndImageIsNull(Integer id);
    @Query("select t.image.name from Teacher t where t.id = :id")
    String getTeacherImageName(Integer id);

}
