package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.exception.BadApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
      String originalFileName=  file.getOriginalFilename();
      logger.info("Filename: {}", originalFileName);
      String filename= UUID.randomUUID().toString();
      String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
      String fileNameWithExtension=filename+extension;
      String fullPathWithFileName=path + fileNameWithExtension;

      logger.info("full image path: {}", fullPathWithFileName);

      if(extension.equalsIgnoreCase(".png") ||extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")){
           //file save
          logger.info("file extension is {}", extension);
          File folder=new File(path);
          if(!folder.exists()){
              //create folder
              folder.mkdirs();
          }//upload file
          Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
          return fileNameWithExtension;
      }
      else{
          throw new BadApiRequestException("File with this" + extension + "not Allowed");
      }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);

        return inputStream;
    }
}
