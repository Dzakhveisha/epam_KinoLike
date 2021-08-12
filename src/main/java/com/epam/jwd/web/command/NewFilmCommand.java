package com.epam.jwd.web.command;

import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Country;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.CountryService;
import com.epam.jwd.web.service.FilmService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class NewFilmCommand implements Command {

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_DESCRIPTION = "descript";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE = "genre";
    private static final String FILE_NAME_PARAMETER = "fileName";
    private static final String PART_NAME = "image";
    private final FilmService filmService;
    private final CountryService countryService;

    public NewFilmCommand() {
        filmService = FilmService.getInstance();
        countryService = CountryService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String name = replaceScripts(new String(request.getParameter(PARAMETER_NAME).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        String description = replaceScripts(new String(request.getParameter(PARAMETER_DESCRIPTION).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        Integer year = Integer.parseInt(request.getParameter(PARAMETER_YEAR));
        FilmGenre genre = FilmGenre.valueOf(request.getParameter(PARAMETER_GENRE));
        String uploadedName = request.getParameter(FILE_NAME_PARAMETER);
        uploadedName = uploadedName.substring(uploadedName.lastIndexOf('\\') + 1);

        String countryName =replaceScripts(new String(request.getParameter(PARAMETER_COUNTRY).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Country country;
        try{
            country = countryService.findByName(countryName);
        }catch(UnknownEntityException e){
            countryService.create(new Country(countryName));
            country = countryService.findByName(countryName);
        }

        Part filePart = request.getPart(PART_NAME);
        try {
            updateImage(request.getReq(),filePart, uploadedName);
        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
        filmService.create(new Movie(name, year, description, country.getId(), genre.getId(), uploadedName));
        return new SimpleCommandResponse("/KinoLike/index.jsp",true);
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

    private String replaceScripts(String string){
        string = string.replaceAll("<","&lt");
        string = string.replaceAll(">","&gt");
        return string;
    }
}
