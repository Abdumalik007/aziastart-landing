package com.azia.landing.service.main;

import com.azia.landing.dto.SchoolInfoDto;
import org.springframework.http.ResponseEntity;

public interface SchoolInfoService {
    ResponseEntity<?> updateSchoolInfo(SchoolInfoDto schoolInfoDto);
    ResponseEntity<?> getSchoolInfoDto();
}
