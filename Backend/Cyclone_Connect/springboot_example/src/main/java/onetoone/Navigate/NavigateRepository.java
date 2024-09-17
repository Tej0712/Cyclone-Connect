package onetoone.Navigate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigateRepository extends JpaRepository<Navigate, Long> {

    // Additional query methods can be defined here
}
