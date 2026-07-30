package poncha.kiet.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poncha.kiet.inventoryservice.model.Product;
import poncha.kiet.inventoryservice.repository.ProductRepository;
import poncha.kiet.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(inventoryService.getProduct(productId));
    }

    @PutMapping("/{productId}/decrease-quantity")
    public ResponseEntity<Product>  decreaseQuantity(@PathVariable("productId") Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.decreaseQuantity(productId, quantity));
    }
}
