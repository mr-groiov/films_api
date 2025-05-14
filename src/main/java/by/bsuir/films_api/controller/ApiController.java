package by.bsuir.films_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.bsuir.films_api.model.MovieEntity;
import by.bsuir.films_api.repo.MovieRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class ApiController {

    private final MovieRepository movieRepository;

    public ApiController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<MovieEntity>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieEntity> getMovieById(@PathVariable Integer id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieEntity> createMovie(@RequestBody MovieEntity movie) {
        MovieEntity savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieEntity> updateMovie(@PathVariable Integer id, @RequestBody MovieEntity movie) {
        Optional<MovieEntity> existingMovie = movieRepository.findById(id);

        if (existingMovie.isPresent()) {
            movie.setMovieId(id); // Убеждаемся, что ID фильма совпадает с URL
            MovieEntity updatedMovie = movieRepository.save(movie);
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}