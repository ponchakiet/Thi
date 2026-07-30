package poncha.kiet.inventoryservice.service;

import poncha.kiet.inventoryservice.model.Product;

public interface InventoryService {
    Product getProduct(Long id);
    Product decreaseQuantity(Long productId,  Integer quantity);
}
