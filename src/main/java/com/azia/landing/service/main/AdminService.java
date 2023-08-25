package com.azia.landing.service.main;

import com.azia.landing.dto.AdminDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> updateAdmin(AdminDto adminDto);
    ResponseEntity<?> findAdminById(Integer id);
}
