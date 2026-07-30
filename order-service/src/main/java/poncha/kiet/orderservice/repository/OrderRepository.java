package poncha.kiet.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poncha.kiet.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
