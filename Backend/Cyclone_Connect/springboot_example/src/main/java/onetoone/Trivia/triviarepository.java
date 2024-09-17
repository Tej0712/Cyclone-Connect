package onetoone.Trivia;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface triviarepository extends JpaRepository<triviaentity, Long> {
}
