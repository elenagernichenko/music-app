package com.itis.service.impl;

import com.itis.service.FileService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public void saveFile(String fileName, Part filePart, String uploadPath) throws IOException {
        log.info("FileServiceImpl save file fileName - {}", fileName);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);
    }

    @Override
    public void downLoadFile(String path, HttpServletResponse response) throws IOException {
        log.info("FileServiceImpl download file path {}", path);
        try (InputStream in = new FileInputStream(path);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
