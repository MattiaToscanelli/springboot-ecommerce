package ch.toscanelli.ecommerce.controller;

import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import ch.toscanelli.ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }
}
