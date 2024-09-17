package onetoone.Organizations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    // Custom query to find organizations by user ID
    @Query("SELECT o FROM Organization o JOIN o.members u WHERE u.userId = :userId")
    List<Organization> findOrganizationsByUserId(@Param("userId") Long userId);
}
