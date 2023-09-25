package com.azia.landing.service.impl;


import com.azia.landing.dto.SubjectDto;
import com.azia.landing.entity.Subject;
import com.azia.landing.entity.Subject;
import com.azia.landing.mapper.TeacherMapper;
import com.azia.landing.repository.SubjectRepository;
import com.azia.landing.service.main.SubjectService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import static com.azia.landing.helper.ResponseEntityHelper.INTERNAL_ERROR;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    public static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);
    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;


    @PostConstruct
    public void init() {
        if(subjectRepository.count() == 0) {
            for(int i = 0; i < 15; i++)
                subjectRepository.save(new Subject());
        }
    }

    @Override
    public ResponseEntity<?> updateSubjects(List<SubjectDto> subjectsDto) {
        try {

            subjectsDto.forEach(s -> {
                Optional<Subject> optional = subjectRepository.findById(s.getId());
                if(optional.isEmpty()) throw new RuntimeException(
                        String.format("Subject with %d id is not found", s.getId())
                );
                Subject subject = optional.get();
                subject.setName(s.getName());
                subjectRepository.save(subject);
            });
            return ResponseEntity.ok(subjectsDto);
        }catch (Exception e) {
            logger.error("Error while updating subjects: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findAllSubjects() {
        List<SubjectDto> subjectsDto = new java.util.ArrayList<>(subjectRepository.findAllByOrderById()
                .stream().map(s ->
                        SubjectDto.builder()
                                .id(s.getId())
                                .name(s.getName())
                                .teacher(teacherMapper.toDto(s.getTeacher()))
                                .build()
                ).toList());
        return ResponseEntity.ok(subjectsDto);
    }




}
