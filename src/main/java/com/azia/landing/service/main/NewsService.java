package com.azia.landing.service.main;

import com.azia.landing.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {
    ResponseEntity<?> createNews(NewsDto newsDto, MultipartFile file);
    ResponseEntity<?> updateNews(NewsDto newsDto, MultipartFile file);
    ResponseEntity<?> findNewsById(Integer id);
    ResponseEntity<?> findAllNewss();
    ResponseEntity<?> deleteNewsById(Integer id);
}
