package com.kevin.movies.service;

import com.kevin.movies.models.Movie;
import com.kevin.movies.repositories.MovieRepository;
import com.kevin.movies.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existingMovie = getMovieById(id);

        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setYear(updatedMovie.getYear());
        existingMovie.setVotes(updatedMovie.getVotes());
        existingMovie.setRating(updatedMovie.getRating());
        existingMovie.setImageUrl(updatedMovie.getImageUrl());

        return movieRepository.save(existingMovie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    public Movie voteMovie(Long id, double rating) {
        Movie movie = getMovieById(id);

        double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);

        movie.setVotes(movie.getVotes() + 1);
        movie.setRating(newRating);

        return movieRepository.save(movie);
    }
}
