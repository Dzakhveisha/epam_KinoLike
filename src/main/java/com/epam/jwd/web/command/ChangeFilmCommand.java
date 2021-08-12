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

        String filmName = new String(request.getParameter(FILM_PARAMETER).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Movie oldFilm = filmService.findByName(filmName);

        String newName = new String(request.getParameter(PARAMETER_NAME).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String newDescription = new String(request.getParameter(PARAMETER_DESCRIPTION).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        int newYear = Integer.parseInt(request.getParameter(PARAMETER_YEAR));
        FilmGenre newGenre = FilmGenre.valueOf(request.getParameter(PARAMETER_GENRE));

        String countryName = new String(request.getParameter(PARAMETER_COUNTRY).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        Country newCountry;
        try{
            newCountry = countryService.findByName(countryName);
        }catch(UnknownEntityException e){
            countryService.create(new Country(countryName));
            newCountry = countryService.findByName(countryName);
        }

        String uploadedName = request.getParameter(FILE_NAME_PARAMETER);
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
}
