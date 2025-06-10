package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomProject.model.Product;
import com.ecommerce.ecomProject.payload.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public interface FileService {

    String uploadImage(String path, MultipartFile file) throws IOException;

}
