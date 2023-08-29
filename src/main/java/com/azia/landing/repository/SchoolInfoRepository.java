package com.azia.landing.repository;

import com.azia.landing.entity.SchoolInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolInfoRepository extends JpaRepository<SchoolInfo, Integer> {

}
