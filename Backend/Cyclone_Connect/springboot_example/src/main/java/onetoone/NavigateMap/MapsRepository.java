package onetoone.NavigateMap;

import onetoone.NavigateMap.Maps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapsRepository extends JpaRepository<Maps, Long> {
    // This interface inherits standard CRUD methods from JpaRepository
    // You can add custom query methods if needed
}

