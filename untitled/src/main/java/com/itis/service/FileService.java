package com.itis.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public interface FileService {

    void saveFile(String fileName, Part filePart, String uploadPathfile) throws IOException;

    void downLoadFile(String path, HttpServletResponse response) throws IOException;
}
