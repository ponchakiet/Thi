package poncha.kiet.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poncha.kiet.orderservice.dto.OrderDTO;
import poncha.kiet.orderservice.dto.ProductDTO;
import poncha.kiet.orderservice.feign.InventoryServiceClient;
import poncha.kiet.orderservice.model.Order;
import poncha.kiet.orderservice.repository.OrderRepository;
import poncha.kiet.orderservice.service.OrderService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    @Override
    public Order createOrder(OrderDTO order) {
        ProductDTO product = inventoryServiceClient.getProduct(order.getProductId());
        if (product.getQuantity() <= 0 || product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product not enough");
        }

        Order orderEntity = new Order();
        orderEntity.setProductId(product.getId());
        orderEntity.setQuantity(order.getQuantity());
        orderEntity.setStatus(Order.Status.PENDING);
        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(order.getQuantity()));
        orderEntity.setTotalPrice(totalPrice);

        inventoryServiceClient.decreaseQuantity(order.getProductId(), order.getQuantity());

        return orderRepository.save(orderEntity);
    }
}
