package com.azia.landing.service.impl;

import com.azia.landing.dto.ApplicantDto;
import com.azia.landing.entity.Applicant;
import com.azia.landing.mapper.ApplicantMapper;
import com.azia.landing.repository.ApplicantRepository;
import com.azia.landing.service.main.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static com.azia.landing.helper.ResponseEntityHelper.*;


@RequiredArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {
    public static final Logger logger = LoggerFactory.getLogger(ApplicantServiceImpl.class);
    private final ApplicantRepository ApplicantRepository;
    private final ApplicantMapper ApplicantMapper;

    @Override
    public ResponseEntity<?> createApplicant(ApplicantDto ApplicantDto) {
        try {
            Applicant Applicant = ApplicantMapper.toEntity(ApplicantDto);
            ApplicantRepository.save(Applicant);
            ApplicantDto.setId(Applicant.getId());
            return OK_MESSAGE();
        } catch (Exception e){
            logger.error("Error while creating student info: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findApplicantById(Integer id) {
        Optional<Applicant> optional = ApplicantRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(ApplicantMapper.toDto(optional.get()));
        return NOT_FOUND();
    }

    @Override
    public ResponseEntity<?> findApplicantByDate(Date from, Date to) {
        try {
            List<ApplicantDto> studentsInfo = ApplicantRepository.findAllByCreatedAtBetween(from, to)
                    .stream().map(ApplicantMapper::toDto).toList();
            return ResponseEntity.ok(studentsInfo);
        }catch (Exception e){
            logger.error("Error while getting students info between two dates: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<ApplicantDto> studentsInfo = ApplicantRepository.findAll()
                .stream().map(ApplicantMapper::toDto).toList();
        return ResponseEntity.ok(studentsInfo);
    }


    @Override
    public ResponseEntity<?> deleteApplicantById(Integer id) {
        Optional<Applicant> ApplicantOptional = ApplicantRepository.findById(id);
        try {
            if(ApplicantOptional.isEmpty())
                return NOT_FOUND();
            ApplicantRepository.delete(ApplicantOptional.get());
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing student info: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }
}
