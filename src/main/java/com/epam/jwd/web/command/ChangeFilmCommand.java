package com.epam.jwd.web.command;

import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.*;
import com.epam.jwd.web.service.CountryService;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChangeFilmCommand implements Command {

    private static final String FILMS_ATTRIBUTE = "films";
    private static final String USERS_ATTRIBUTE = "users";
    private static final String ADMIN_ATTRIBUTE = "admin";
    private static final String STATUSES_ATTRIBUTE = "statuses";
    private static final String GENRES_ATTRIBUTE = "genres";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_DESCRIPTION = "descript";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE = "genre";
    private static final String FILM_PARAMETER = "film";
    private static final String FILE_NAME_PARAMETER = "fileName";
    private static final String USER_ATTRIBUTE = "user";
    private static final String PART_NAME = "image";
    private static final String ERROR_ATTRIBUTE = "error";


    private final FilmService filmService;
    private final UserService userService;
    private final CountryService countryService;

    public ChangeFilmCommand() {
        filmService = FilmService.getInstance();
        userService = UserService.getInstance();
        countryService = CountryService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        String filmName = request.getParameter(FILM_PARAMETER);
        String newName = request.getParameter(PARAMETER_NAME);
        String newDescription = request.getParameter(PARAMETER_DESCRIPTION);
        String yearString = request.getParameter(PARAMETER_YEAR);
        String genreString = request.getParameter(PARAMETER_GENRE);
        String uploadedName = request.getParameter(FILE_NAME_PARAMETER);
        String countryName = request.getParameter(PARAMETER_COUNTRY);

        if(filmName == null || newName == null || newDescription == null ||
                yearString == null || genreString == null || uploadedName == null || countryName == null){
            request.setAttribute(ERROR_ATTRIBUTE, "Not enough data!");
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }


        filmName = replaceScripts(new String(filmName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Movie oldFilm;
        try {
            oldFilm = filmService.findByName(filmName);
        }catch (UnknownEntityException e){
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return new SimpleCommandResponse("/WEB-INF/jsp/error.jsp",false);
        }

        newName = replaceScripts(new String(newName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        newDescription = replaceScripts(new String(newDescription.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        int newYear = Integer.parseInt(yearString);
        FilmGenre newGenre = FilmGenre.getGenreByName(genreString);

        countryName = replaceScripts(new String(countryName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        Country newCountry;
        try{
            newCountry = countryService.findByName(countryName);
        }catch(UnknownEntityException e){
            countryService.create(new Country(countryName));
            newCountry = countryService.findByName(countryName);
        }

        uploadedName = new String(uploadedName.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
        uploadedName = uploadedName.substring(uploadedName.lastIndexOf('\\') + 1);

        Part filePart = request.getPart(PART_NAME);
        try {
            if (filePart.getSize() != 0)
            updateImage(request.getReq(),filePart, uploadedName);
        } catch (IOException e) {
            uploadedName = oldFilm.getImagePath();
            //TODO
        }

        filmService.update(new Movie(oldFilm.getId(), newName, newYear, newDescription, newCountry.getId(), oldFilm.getRating(),
                newGenre.getId(), uploadedName));

        final HttpSession session = request.getCurrentSession().get();
        String curUserName = (String) session.getAttribute(USER_ATTRIBUTE);
        User curUser = userService.findByLogin(curUserName);
        List<Movie> films = filmService.findAll();
        List<User> users = userService.findAll();
        List<UserStatus> statuses = userService.findAllStatuses();
        List<FilmGenre> genres = filmService.findAllGenres();

        request.setAttribute(GENRES_ATTRIBUTE, genres);
        request.setAttribute(STATUSES_ATTRIBUTE, statuses);
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(USERS_ATTRIBUTE, users);
        request.setAttribute(ADMIN_ATTRIBUTE, curUser);
        return new SimpleCommandResponse("/KinoLike/controller?command=show_admin",true);
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
