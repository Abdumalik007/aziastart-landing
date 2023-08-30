package com.azia.landing.controller;

import com.azia.landing.dto.NewsDto;
import com.azia.landing.service.main.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNews(@ModelAttribute NewsDto newsDto,
                                           @RequestParam(value = "file", required = false) MultipartFile file){
        return newsService.createNews(newsDto, file);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateNews(@ModelAttribute NewsDto newsDto,
                                           @RequestParam(value = "file", required = false) MultipartFile file){
        return newsService.updateNews(newsDto, file);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> findAllNews(){
        return newsService.findAllNewss();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findNewsById(@PathVariable Integer id){
        return newsService.findNewsById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Integer id){
        return newsService.deleteNewsById(id);
    }
}
