package com.azia.landing.service.impl;

import com.azia.landing.entity.Admin;
import com.azia.landing.entity.User;
import com.azia.landing.dto.AdminDto;
import com.azia.landing.dto.UserDto;
import com.azia.landing.mapper.AdminMapper;
import com.azia.landing.repository.AdminRepository;
import com.azia.landing.role.Role;
import com.azia.landing.service.main.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.azia.landing.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static com.azia.landing.helper.ResponseEntityHelper.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    public static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        if(adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .user(
                            User.builder()
                                    .email("aziastart@gmail.com")
                                    .password(encoder.encode("190271"))
                                    .role(Role.ADMIN).build()
                    )
                    .firstName("Admin")
                    .lastName("Adminov")
                    .build();
            adminRepository.save(admin);
        }
    }

    @Override
    public ResponseEntity<?> updateAdmin(AdminDto adminDto) {
        try {
            Optional<Admin> optional = adminRepository.findById(adminDto.getId());
            if(optional.isEmpty())
                return NOT_FOUND();

            Admin admin = optional.get();
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());

            updateAdminUser(adminDto, admin);

            adminRepository.save(admin);
            return ResponseEntity.ok(adminDto);
        }catch (Exception e){
            logger.error("Error while updating a admin".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }


    @Override
    public ResponseEntity<?> findAdminById(Integer id) {
        Optional<Admin> optional = adminRepository.findById(id);
        if(optional.isPresent()) return ResponseEntity.ok(adminMapper.toDto(optional.get()));
        return NOT_FOUND();
    }



    private void updateAdminUser(AdminDto adminDto, Admin admin){
        User user = admin.getUser();
        UserDto userDto = adminDto.getUser();
        user.setEmail(adminDto.getUser().getEmail());
        if(userDto.getPassword().length() > 0)
            user.setPassword(encoder.encode(userDto.getPassword()));
        admin.setUser(user);
    }

}

