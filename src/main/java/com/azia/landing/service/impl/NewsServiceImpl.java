package com.azia.landing.service.impl;

import com.azia.landing.dto.NewsDto;
import com.azia.landing.entity.Image;
import com.azia.landing.entity.News;
import com.azia.landing.mapper.NewsMapper;
import com.azia.landing.repository.ImageRepository;
import com.azia.landing.repository.NewsRepository;
import com.azia.landing.service.main.NewsService;
import com.azia.landing.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.azia.landing.helper.ResponseEntityHelper.*;
import static com.azia.landing.util.ImageUtil.IMAGE_PATH;
import static com.azia.landing.util.ImageUtil.buildImage;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    public static final Logger logger = LoggerFactory.getLogger(ApplicantServiceImpl.class);
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<?> createNews(NewsDto newsDto, MultipartFile file) {
        try {
            News news = newsMapper.toEntity(newsDto);
            Image image = buildImage(file);
            news.setImage(image);
            newsRepository.save(news);

            newsDto = newsMapper.toDto(news);
            return ResponseEntity.ok(newsDto);
        }catch (Exception e) {
            logger.error("Error while creating a news: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }



    @Override
    public ResponseEntity<?> updateNews(NewsDto newsDto, MultipartFile file) {
        try {
            Optional<News> optional = newsRepository.findById(newsDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();

            News news = optional.get();
            if(file != null)
                updateImage(news, file);

            news.setTitle(newsDto.getTitle());
            news.setContent(newsDto.getContent());
            news.setCreatedAt(newsDto.getCreatedAt());

            newsRepository.save(news);
            newsDto = newsMapper.toDto(news);

            return ResponseEntity.ok(newsDto);
        } catch (Exception e) {
            logger.error("Error while updating a news: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    @Override
    public ResponseEntity<?> findNewsById(Integer id) {
        Optional<News> optional = newsRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(newsMapper.toDto(optional.get()));
        return NOT_FOUND();
    }

    @Override
    public ResponseEntity<?> findAllNewss() {
        List<NewsDto> news = newsRepository.findAll()
                .stream().map(newsMapper::toDto).toList();
        return ResponseEntity.ok(news);
    }

    @Override
    public ResponseEntity<?> deleteNewsById(Integer id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        try {
            if(newsOptional.isEmpty())
                return NOT_FOUND();
            Files.delete(Path.of(IMAGE_PATH + "/" + newsOptional.get().getImage().getPath()));
            newsRepository.delete(newsOptional.get());
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing news: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }





    private void updateImage(News news, MultipartFile file) throws IOException {
        Image oldImage = news.getImage();
        imageRepository.delete(oldImage);
        Files.delete(Path.of(oldImage.getPath()));

        news.setImage(buildImage(file));
    }


}
