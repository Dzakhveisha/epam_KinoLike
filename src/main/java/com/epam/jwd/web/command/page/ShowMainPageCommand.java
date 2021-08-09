package com.epam.jwd.web.command.page;

import com.epam.jwd.web.command.Command;
import com.epam.jwd.web.command.CommandRequest;
import com.epam.jwd.web.command.CommandResponse;
import com.epam.jwd.web.exception.UnknownEntityException;
import com.epam.jwd.web.model.FilmGenre;
import com.epam.jwd.web.model.Movie;
import com.epam.jwd.web.model.Review;
import com.epam.jwd.web.model.User;
import com.epam.jwd.web.service.FilmService;
import com.epam.jwd.web.service.ReviewService;
import com.epam.jwd.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMainPageCommand implements Command {

    private static final String GENRES_ATTRIBUTE = "genres";
    private static final String HAVE_REVIEWS_ATTRIBUTE = "filmReviews";
    private static final String FILMS_ATTRIBUTE = "films";
    public static final String SORT_PARAMETER = "sort";
    public static final String GENRE_PARAMETER = "genre";
    public static final String SORT_TYPE_NEW = "new";
    public static final String SORT_TYPE_POPULAR = "popular";
    public static final String SORT_TYPE_SEARCH = "search";

    private final FilmService filmService;
    private final ReviewService reviewService;
    private final UserService userService;

    public ShowMainPageCommand() {
        filmService = FilmService.getInstance();
        reviewService = ReviewService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        List<FilmGenre> genres = filmService.findAllGenres();
        request.setAttribute(GENRES_ATTRIBUTE, genres);
        List<Movie> films = getFilms(request);
        request.setAttribute(FILMS_ATTRIBUTE, films);
        request.setAttribute(HAVE_REVIEWS_ATTRIBUTE, getFilmsAndReviewsMap(request, films));

        return new CommandResponse() {
            @Override
            public String getPath() {
                return "/WEB-INF/jsp/main.jsp";
            }

            @Override
            public boolean isRedirect() {
                return false;
            }
        };
    }

    private List<Movie> getFilms(CommandRequest request) {
        List<Movie> films = new ArrayList<>();
        String sortType = request.getParameter(SORT_PARAMETER);
        if (sortType != null) {
            switch (sortType) {
                case SORT_TYPE_NEW:
                    films = filmService.findAllNew();
                    break;
                case SORT_TYPE_POPULAR:
                    films = filmService.findAllPopular();
                    break;
                case SORT_TYPE_SEARCH:
                    String searchStr = new String(request.getParameter("search").getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
                    films = filmService.findAllByString(searchStr);
                    break;
            }
        } else {
            films = filmService.findAll();
        }
        String genreName = request.getParameter(GENRE_PARAMETER);
        if (genreName != null) {
            films = filmService.finAllByGenre(FilmGenre.valueOf(genreName));
        }
        return films;
    }
    private Map<Long, Review> getFilmsAndReviewsMap(CommandRequest request, List<Movie> films){
        Map<Long, Review> filmMap = new HashMap<>();
        final HttpSession session = request.getCurrentSession().get();
        String name = (String) session.getAttribute("user");
        if (name != null) {
            User curUser = userService.findByLogin(name);

            for (Movie film : films) {
                try {
                    Review review = reviewService.findBy(film, curUser);
                    filmMap.put(film.getId(), review);
                } catch (UnknownEntityException e) {
                    filmMap.put(film.getId(), null);
                }
            }
        } else {
            for (Movie film : films) {
                filmMap.put(film.getId(), null);
            }
        }
        return filmMap;
    }
}
