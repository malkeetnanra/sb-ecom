package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomProject.model.Product;
import com.ecommerce.ecomProject.payload.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServicImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File names of the current/ original file

        String originalFileName = file.getName();

        //Generate a random ID
        String randomID = UUID.randomUUID().toString();

        //check if path exist else create
        String fileName = randomID.concat(originalFileName
                .substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.pathSeparator + fileName;
        /*File separator is used for adding / to locate the file
        Here, user path separator because in case we are using it on any OS,
         it shall pick up the inbuilt function hence, not hardcoded
         */

        //check if file exists
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();
        //upload to server and rename the file uniquely(to avoid name conflict)
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //returning file name
        return fileName;
    }



}
