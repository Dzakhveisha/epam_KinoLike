package com.epam.jwd.web.command;

import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.FilmService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


public class NewFilmCommand implements Command {

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_DESCRIPTION = "descript";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE = "genre";
    private final FilmService filmService;

    public NewFilmCommand() {
        filmService = FilmService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String name = new String(request.getParameter(PARAMETER_NAME).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String description = new String(request.getParameter(PARAMETER_DESCRIPTION).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Integer year = Integer.parseInt(request.getParameter(PARAMETER_YEAR));
        String country = new String(request.getParameter(PARAMETER_COUNTRY).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        FilmGenre genre = FilmGenre.valueOf(request.getParameter(PARAMETER_GENRE));
        String uploadedName = request.getParameter("fileName");
        uploadedName = uploadedName.substring(uploadedName.lastIndexOf('\\') + 1);

        Part filePart = request.getPart("image");
        try {
            updateImage(request.getReq(),filePart, uploadedName);
        } catch (IOException e) {
            e.printStackTrace(); //todo
        }
        filmService.create(new Movie(name, year, description, 1, genre.getId(), uploadedName));
        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/KinoLike/index.jsp";
            }

            @Override
            public boolean isRedirect() {
                return true;
            }
        };
    }

    private void updateImage(HttpServletRequest request, Part part, String uploadedName) throws IOException {
        ServletContext servletContext = request.getServletContext();
        String uploadDirectory = servletContext.getInitParameter("IMAGE_UPLOAD_PATH");
        String savePath = uploadDirectory + uploadedName;
        writePart(part, savePath);
    }

    private void writePart(Part part, String filename) throws IOException {
        InputStream in = part.getInputStream();
        FileOutputStream out = new FileOutputStream(filename);
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len != -1) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }
}
