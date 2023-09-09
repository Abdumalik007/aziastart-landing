package com.azia.landing.controller;


import com.azia.landing.entity.Video;
import com.azia.landing.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.azia.landing.helper.ResponseEntityHelper.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoRepository videoRepository;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{url}")
    public ResponseEntity<?> addVideoUrl(@PathVariable String url){
        if(videoRepository.count() == 1){
            Video video = videoRepository.findAll().get(0);
            video.setUrl(url);
            videoRepository.save(video);
            return ResponseEntity.ok(video);
        }else {
            Video video = new Video();
            video.setUrl(url);
            videoRepository.save(video);
            return ResponseEntity.ok(video);
        }
    }


    @GetMapping
    public ResponseEntity<?> findAdminById(){
        if(videoRepository.count() == 0)
            return NOT_FOUND();
        Video video = videoRepository.findAll().get(0);
        return ResponseEntity.ok(video);
    }

}
