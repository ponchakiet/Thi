package poncha.kiet.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poncha.kiet.inventoryservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
