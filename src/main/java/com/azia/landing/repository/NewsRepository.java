package com.azia.landing.repository;

import com.azia.landing.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsNewsByIdAndImageIsNull(Integer id);
    @Query("select n.image.name from News n where n.id = :id")
    String getNewsImageName(Integer id);
}
