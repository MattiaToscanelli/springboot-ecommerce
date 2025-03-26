package ch.toscanelli.ecommerce.service;

import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;

public interface ICheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

}
