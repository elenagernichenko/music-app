package com.itis.servlet.userServlet;

import com.itis.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {
    private FileService fileService;


    @Override
    public void init() throws ServletException {
        fileService = (FileService) getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getParameter("filename");
        response.setContentType("application/octet-stream");

        String downLoadPath = getServletContext().getRealPath("") + File.separator + "/storage/" + filename;
        try {
            setResponseHeader(request, response, filename);
            fileService.downLoadFile(downLoadPath, response);
        } catch (Exception exception) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        boolean isInternetExplorer = (userAgent.contains("MSIE") || userAgent.contains("Trident"));

        String encodedFilename;
        if (isInternetExplorer) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8");
        } else {
            encodedFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        }

        String headerValue = "attachment; filename=\"" + encodedFilename + "\"";
        response.setHeader("Content-Disposition", headerValue);
    }
}
