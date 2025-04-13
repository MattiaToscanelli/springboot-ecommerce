package ch.toscanelli.ecommerce.service;

import ch.toscanelli.ecommerce.dto.PaymentInfo;
import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface ICheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
