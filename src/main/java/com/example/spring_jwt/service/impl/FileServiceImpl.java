package com.example.spring_jwt.service.impl;

import com.example.spring_jwt.service.FileService;
import org.springframework.core.io.ResourceLoader;

public class FileServiceImpl implements FileService {

    private final ResourceLoader resourceLoader;

    public FileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
