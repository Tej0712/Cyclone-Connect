package onetoone.Notes_with_userID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotesRepository extends JpaRepository<Notes_Entity, Long> {
}
