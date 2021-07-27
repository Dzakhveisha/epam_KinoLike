package com.epam.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        String imageUploadPath = request.getServletContext().getInitParameter("IMAGE_UPLOAD_PATH");
        String imagePath = imageUploadPath + filename;

        try (InputStream is = new FileInputStream(imagePath)) {
            // it is the responsibility of the container to close output stream
            OutputStream os = response.getOutputStream();

            response.setContentType("image/jpeg");

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
