package ch.toscanelli.ecommerce.service;

import ch.toscanelli.ecommerce.dao.CustomerRepository;
import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import ch.toscanelli.ecommerce.entity.Customer;
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

        // get customer from dto
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String email = customer.getEmail();
        Customer customerFormDb = customerRepository.findByEmail(email);

        if (customerFormDb != null) {
            // customer exists
            customer = customerFormDb;
        }

        // populate customer with order
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
