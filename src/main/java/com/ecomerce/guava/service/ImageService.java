package com.ecomerce.guava.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {

    public String saveImage(MultipartFile file) throws IOException;
}
