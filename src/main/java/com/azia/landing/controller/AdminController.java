package com.azia.landing.controller;


import com.azia.landing.dto.AdminDto;
import com.azia.landing.service.main.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestBody AdminDto adminDto){
        return adminService.updateAdmin(adminDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findAdminById(@PathVariable Integer id){
        return adminService.findAdminById(id);
    }
}

