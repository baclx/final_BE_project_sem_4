package com.example.spring_jwt.controller;

import com.example.spring_jwt.entities.*;
import com.example.spring_jwt.model.request.UpdateUser;
import com.example.spring_jwt.model.request.UpdateUserReq;
import com.example.spring_jwt.model.response.UserDetail;
import com.example.spring_jwt.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    SpecializationService specializationService;

    @Autowired
    PatientService patientService;

    public String hello() {
        return "hello user";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDetail>> getAllUser(){
        List<User> users;
        List<UserDetail> userDetails = new ArrayList<>();
        try {
            users = userService.findAll();
            if(!CollectionUtils.isEmpty(users)){
                for (User user : users){
                    UserDetail userDetail = mappingUserDetail(user);
                    userDetails.add(userDetail);
                }
            }
            if(!CollectionUtils.isEmpty(userDetails)){
                return ResponseEntity.ok(userDetails);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return (ResponseEntity<List<UserDetail>>) ResponseEntity.notFound();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody UpdateUser user){
        User oldUser = userService.getUserById(id);
        if (oldUser == null){
            return ResponseEntity.notFound().build();
        }else{
            oldUser.setFullName(user.getFullName());
            oldUser.setEmail(user.getEmail());

            return ResponseEntity.ok(oldUser);
        }
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> update(@PathVariable("userId") Integer userId,@RequestBody UpdateUserReq userUpdate){
        try {
            User oldUser = userService.getUserById(userId);
            if (oldUser == null){
                return ResponseEntity.notFound().build();
            }
            oldUser.setEmail(userUpdate.getEmail());
            oldUser.setImage(userUpdate.getImage());
            oldUser.setFullName(userUpdate.getFullName());
            mappingUser(oldUser, userUpdate);
            Patient patient = oldUser.getPatient();
            LocalDate now = LocalDate.now();
            int age = now.getYear() - userUpdate.getDateOfBirth().getYear();
            patient.setHeight(userUpdate.getHeight());
            patient.setWeight(userUpdate.getWeight());
            patient.setDateOfBirth(userUpdate.getDateOfBirth());
            patient.setAddress(userUpdate.getAddress());
            patient.setPhoneNumber(userUpdate.getPhoneNumber());
            patient.setAge(age);
            patient.setUser(oldUser);
            patientService.savePatient(patient);
            //oldUser.setPatient(patient);
            userService.save(oldUser);
            return ResponseEntity.ok("Update user thanh cong");
        }catch (Exception e){
            e.printStackTrace();
        }
        return (ResponseEntity<String>) ResponseEntity.badRequest();
    }

    private static void mappingUser(User user, UpdateUserReq userDetail){
        user.setEmail(userDetail.getEmail());
        user.setImage(userDetail.getImage());
        user.setGender(userDetail.getGender());
    }

    private static UserDetail mappingUserDetail(User user){
        UserDetail userDetail = new UserDetail();
        List<String> roleNames = new ArrayList<>();
        for (Role role : user.getRoles()){
            roleNames.add(role.getName());
        }
        userDetail.setRoleNames(roleNames);
        userDetail.setEmail(user.getEmail());
        userDetail.setFullName(user.getFullName());
        if(user.getPatient() != null){
            userDetail.setAddress(user.getPatient().getAddress() != null ? user.getPatient().getAddress() : "");
            userDetail.setAge(user.getPatient().getAge() != null ? user.getPatient().getAge() : null);
            userDetail.setWeight(user.getPatient().getWeight() != null ? user.getPatient().getWeight() : "");
            userDetail.setHeight(user.getPatient().getHeight() != null ? user.getPatient().getHeight(): "");
            userDetail.setPhoneNumber(user.getPatient().getPhoneNumber() != null ? user.getPatient().getPhoneNumber(): "");
            userDetail.setDateOfBirth(user.getPatient().getDateOfBirth() != null ? user.getPatient().getDateOfBirth() : null);
            userDetail.setImage(user.getImage() != null ? userDetail.getImage() : "");
            userDetail.setGender(user.getGender() != null ? userDetail.getGender() : "");
        }

        return userDetail;
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<UserDetail> getByUserId(@PathVariable("userId") Integer userId){
        try {
            User user = userService.getUserById(userId);
            UserDetail userDetail = mappingUserDetail(user);
            return ResponseEntity.ok(userDetail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ( ResponseEntity<UserDetail>) ResponseEntity.notFound();
    }

    @GetMapping("/listUser/{role}")
    public ResponseEntity<?> findAllByRole(@PathVariable("role") String role) {
        try {
            List<User> users = userService.findAllByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<UserDetail> userDetails = users.stream().map(UserController::mappingUserDetail).collect(Collectors.toList());
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id){
        User deleteUser = userService.getUserById(id);
        if (deleteUser == null){
            return ResponseEntity.notFound().build();
        }else{
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }


}
