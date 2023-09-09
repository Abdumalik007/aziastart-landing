package com.azia.landing.service.impl;

import com.azia.landing.dto.SchoolInfoDto;
import com.azia.landing.entity.Admin;
import com.azia.landing.entity.SchoolInfo;
import com.azia.landing.entity.User;
import com.azia.landing.mapper.SchoolInfoMapper;
import com.azia.landing.repository.SchoolInfoRepository;
import com.azia.landing.role.Role;
import com.azia.landing.service.main.SchoolInfoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static com.azia.landing.helper.ResponseEntityHelper.INTERNAL_ERROR;

@RequiredArgsConstructor
@Service
public class SchoolInfoServiceImpl implements SchoolInfoService {
    public static final Logger logger = LoggerFactory.getLogger(SchoolInfoServiceImpl.class);
    private final SchoolInfoRepository schoolInfoRepository;
    private final SchoolInfoMapper schoolInfoMapper;

    @PostConstruct
    public void init() {
        if(schoolInfoRepository.count() == 0) {
            SchoolInfo schoolInfo = SchoolInfo.builder()
                    .branchAmount(2)
                    .graduateAmount(1200)
                    .studentAmount(1500)
                    .mediumOptScore(145)
                    .build();
            schoolInfoRepository.save(schoolInfo);
        }
    }
    @Override
    public ResponseEntity<?> updateSchoolInfo(SchoolInfoDto schoolInfoDto) {
        try {
            SchoolInfo schoolInfo = schoolInfoMapper.toEntity(schoolInfoDto);
            schoolInfoRepository.save(schoolInfo);
            return ResponseEntity.ok(schoolInfoDto);
        }catch (Exception e){
            logger.error("Error while updating school info: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> getSchoolInfoDto() {
        try {
            SchoolInfo schoolInfo = schoolInfoRepository.findAll().get(0);
            return ResponseEntity.ok(schoolInfoMapper.toDto(schoolInfo));
        }catch (Exception e){
            logger.error("Error while retrieving school info: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }
}
