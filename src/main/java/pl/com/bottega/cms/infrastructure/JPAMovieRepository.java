package pl.com.bottega.cms.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.cms.domain.Movie;
import pl.com.bottega.cms.domain.repositories.GenericJPARepository;
import pl.com.bottega.cms.domain.repositories.MovieRepository;

import javax.persistence.EntityManager;

@Component
public class JPAMovieRepository extends GenericJPARepository<Movie> implements MovieRepository {

    private EntityManager entityManager;

    public JPAMovieRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Movie movie) {
        entityManager.persist(movie);
    }

    @Override
    public Movie get(Long movieId) {
        Movie movie = entityManager.find(Movie.class, movieId);
        if (movie == null)
            throw new NoSuchEntityException();
        return movie;
    }
}
