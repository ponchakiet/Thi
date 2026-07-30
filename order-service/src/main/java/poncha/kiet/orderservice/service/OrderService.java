package poncha.kiet.orderservice.service;

import poncha.kiet.orderservice.dto.OrderDTO;
import poncha.kiet.orderservice.model.Order;

public interface OrderService {
    Order createOrder(OrderDTO order);
}
