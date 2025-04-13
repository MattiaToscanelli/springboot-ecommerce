package ch.toscanelli.ecommerce.controller;

import ch.toscanelli.ecommerce.dto.PaymentInfo;
import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import ch.toscanelli.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 @RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        String paymentString = paymentIntent.toJson();

        return new ResponseEntity<>(paymentString, HttpStatus.OK);
    }
}
