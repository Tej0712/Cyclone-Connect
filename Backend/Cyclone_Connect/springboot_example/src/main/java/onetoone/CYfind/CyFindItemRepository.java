package onetoone.CYfind;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CyFindItemRepository extends JpaRepository<CyFindItem, Long> {
    // Here, you can define any custom query methods you might need.
    // For example, if you want to find items by category, you can add:
    // List<CyFindItem> findByCategory(String category);
}
