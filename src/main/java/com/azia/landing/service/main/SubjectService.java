package com.azia.landing.service.main;


import com.azia.landing.dto.SubjectDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    ResponseEntity<?> updateSubjects(List<SubjectDto> subjects);
    ResponseEntity<?> findAllSubjects();
}
