package poncha.kiet.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poncha.kiet.orderservice.dto.ProductDTO;

@FeignClient(name = "inventory-service", url = "http://localhost:8082")
public interface InventoryServiceClient {
    @GetMapping("/api/inventory/{productId}")
    ProductDTO getProduct(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory/{productId}/decrease-quantity")
    ProductDTO decreaseQuantity(@PathVariable("productId") Long productId, @RequestParam Integer quantity);
}
