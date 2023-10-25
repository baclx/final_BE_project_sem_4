package com.example.spring_jwt.model.request;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class UpdateUserToDoctorReq {
    private Integer userId;
    private Integer specId;
}
