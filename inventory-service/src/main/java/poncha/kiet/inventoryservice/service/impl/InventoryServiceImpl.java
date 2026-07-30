package poncha.kiet.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import poncha.kiet.inventoryservice.model.Product;
import poncha.kiet.inventoryservice.repository.ProductRepository;
import poncha.kiet.inventoryservice.service.InventoryService;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final ProductRepository productRepository;
    @Override
    public Product getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return product;
    }

    @Override
    public Product decreaseQuantity(Long productId,  Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getQuantity() < quantity || product.getQuantity() <= 0) {
            throw new RuntimeException("Quantity out of range");
        }

        product.setQuantity(product.getQuantity()-quantity);

        return productRepository.save(product);
    }
}
