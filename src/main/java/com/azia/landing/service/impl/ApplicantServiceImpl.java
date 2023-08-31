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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.azia.landing.helper.ResponseEntityHelper.*;


@RequiredArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {
    public static final Logger logger = LoggerFactory.getLogger(ApplicantServiceImpl.class);
    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    @Override
    public ResponseEntity<?> createApplicant(ApplicantDto ApplicantDto) {
        try {
            Applicant applicant = applicantMapper.toEntity(ApplicantDto);
            applicant.setCreatedAt(LocalDate.now());
            applicant.setIsContacted(false);
            applicantRepository.save(applicant);
            ApplicantDto.setId(applicant.getId());
            return OK_MESSAGE();
        } catch (Exception e){
            logger.error("Error while creating applicants: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findApplicantById(Integer id) {
        Optional<Applicant> optional = applicantRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(applicantMapper.toDto(optional.get()));
        return NOT_FOUND();
    }

    @Override
    public ResponseEntity<?> findApplicantByDate(LocalDate from, LocalDate to) {
        try {
            List<ApplicantDto> applicants = applicantRepository.findAllByCreatedAtBetweenOrderByCreatedAt(from, to)
                    .stream().map(applicantMapper::toDto).toList();
            return ResponseEntity.ok(applicants);
        }catch (Exception e){
            logger.error("Error while getting applicants between two dates: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<ApplicantDto> applicants = applicantRepository.findByOrderByCreatedAt()
                .stream().map(applicantMapper::toDto).toList();
        return ResponseEntity.ok(applicants);
    }


    @Override
    public ResponseEntity<?> deleteApplicantById(Integer id) {
        Optional<Applicant> ApplicantOptional = applicantRepository.findById(id);
        try {
            if(ApplicantOptional.isEmpty())
                return NOT_FOUND();
            applicantRepository.delete(ApplicantOptional.get());
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while removing applicants: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> isApplicantContacted(Integer applicantId, Boolean isContacted) {
        try {
            applicantRepository.isContacted(isContacted, applicantId);
            return OK_MESSAGE();
        }catch (Exception e){
            logger.error("Error while setting value for isContacted column: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


}
