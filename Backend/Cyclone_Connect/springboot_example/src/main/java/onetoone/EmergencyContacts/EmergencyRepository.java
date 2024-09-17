package onetoone.EmergencyContacts; // This should match your Emergency entity package structure

import onetoone.EmergencyContacts.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findByContactNameContainingIgnoreCase(String contactName);


}
