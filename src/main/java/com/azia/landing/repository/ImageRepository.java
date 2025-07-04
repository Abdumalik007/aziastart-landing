package com.azia.landing.repository;

import com.azia.landing.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
