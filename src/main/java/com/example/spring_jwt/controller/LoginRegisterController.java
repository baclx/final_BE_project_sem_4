package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.Patient;
import com.example.spring_jwt.entities.Role;
import com.example.spring_jwt.entities.User;
import com.example.spring_jwt.jwt.JwtResponse;
import com.example.spring_jwt.jwt.JwtService;
import com.example.spring_jwt.model.request.LoginModel;
import com.example.spring_jwt.model.request.RegisterModel;
import com.example.spring_jwt.service.PatientService;
import com.example.spring_jwt.service.RoleService;
import com.example.spring_jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class LoginRegisterController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByEmail(user.getEmail());

            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getFullName()));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai email hoặc mật khẩu!");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterModel registerModel) {

        try {
            User user = new User();
            user.setPassword(registerModel.getPassword());
            user.setFullName(registerModel.getFullName());
            user.setEmail(registerModel.getEmail());
            Patient patient = new Patient();
            patient.setAddress("New Address");
            patient.setAge(0);
            if (Objects.nonNull(userService.findByEmail(user.getEmail()))) {
                throw new Exception("Đã tồn tại người dùng, vui lòng chọn tên đăng nhập khác");
            }
            String password = user.getPassword();
            Set<Role> userRoles = user.getRoles();
            System.out.println(userRoles);
            if (userRoles == null) {
                Set<Role> setRoles = new HashSet<Role>();
                Role role = roleService.findByName("ROLE_USER");
                setRoles.add(role);
                user.setRoles(setRoles);
            }
            userService.save(user);
            patient.setUser(user);
            patientService.savePatient(patient);
            return ResponseEntity.ok(user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
