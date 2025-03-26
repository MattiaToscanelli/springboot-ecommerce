package ch.toscanelli.ecommerce.service;

import ch.toscanelli.ecommerce.dao.CustomerRepository;
import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import ch.toscanelli.ecommerce.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckoutService implements ICheckoutService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        purchase.getOrderItems().forEach(order::add);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        purchase.getCustomer().add(order);

        // save to the database
        customerRepository.save(purchase.getCustomer());

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
