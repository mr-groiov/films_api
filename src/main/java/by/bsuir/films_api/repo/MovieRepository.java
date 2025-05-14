package by.bsuir.films_api.repo;

import by.bsuir.films_api.model.MovieEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

}
