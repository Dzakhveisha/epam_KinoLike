package com.epam.jwd.web.command;

import com.epam.jwd.web.Validator.Validator;
import com.epam.jwd.web.exception.DataIsNotValidateException;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.Country;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.service.CountryService;
import com.epam.jwd.web.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class NewFilmCommand implements Command {
    static final Logger LOGGER = LogManager.getRootLogger();

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_DESCRIPTION = "descript";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE = "genre";
    private static final String FILE_NAME_PARAMETER = "fileName";
    private static final String PART_NAME = "image";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String NOT_ENOUGH_DATA_MSG = "Not enough data!";
    private static final String POSTER_HAS_BEEN_NOT_ADDED_MSG = "Movie poster has not been added!";

    private final FilmService filmService;
    private final CountryService countryService;
    private final Validator dataValidator;

    public NewFilmCommand() {
        filmService = FilmService.getInstance();
        countryService = CountryService.getInstance();
        dataValidator = Validator.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String name = request.getParameter(PARAMETER_NAME);
        String description = request.getParameter(PARAMETER_DESCRIPTION);
        String yearString = request.getParameter(PARAMETER_YEAR);
        String genreString = request.getParameter(PARAMETER_GENRE);
        String uploadedName = request.getParameter(FILE_NAME_PARAMETER);
        String countryString = request.getParameter(PARAMETER_COUNTRY);

        if(name == null || description == null || yearString == null || genreString == null
                || uploadedName == null || countryString == null){
            request.setAttribute(ERROR_ATTRIBUTE, NOT_ENOUGH_DATA_MSG);
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }

        name = replaceScripts(new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        description = replaceScripts(new String(description.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        String countryName = replaceScripts(new String(countryString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Integer year = Integer.parseInt(yearString);
        uploadedName = uploadedName.substring(uploadedName.lastIndexOf('\\') + 1);

        FilmGenre genre;
        try{
            genre = FilmGenre.getGenreByName(genreString);
        }catch(UnknownEntityException e){
            LOGGER.error(e.getMessage() + " Genre Name:" + genreString);
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }

        Country country;
        try{
            country = countryService.findByName(countryName);
        }catch(UnknownEntityException e){
            LOGGER.error(e.getMessage() + " Country Name:" + genreString);
            countryService.create(new Country(countryName));
            country = countryService.findByName(countryName);
        }

        Part filePart = request.getPart(PART_NAME);
        try {
            updateImage(request.getReq(),filePart, uploadedName);
        } catch (IOException e) {
            LOGGER.error(e.getMessage() + POSTER_HAS_BEEN_NOT_ADDED_MSG);
        }

        Movie newMovie = new Movie(name, year, description, country.getId(), genre.getId(), uploadedName);
        try{
           dataValidator.validateFilm(newMovie);
        } catch (DataIsNotValidateException e) {
            LOGGER.error(e.getMessage());
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }
        filmService.create(newMovie);
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
