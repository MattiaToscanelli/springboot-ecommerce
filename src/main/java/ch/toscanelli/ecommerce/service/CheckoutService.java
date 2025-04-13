package ch.toscanelli.ecommerce.service;

import ch.toscanelli.ecommerce.dao.CustomerRepository;
import ch.toscanelli.ecommerce.dto.PaymentInfo;
import ch.toscanelli.ecommerce.dto.Purchase;
import ch.toscanelli.ecommerce.dto.PurchaseResponse;
import ch.toscanelli.ecommerce.entity.Customer;
import ch.toscanelli.ecommerce.entity.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutService implements ICheckoutService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${stripe.key.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = secretKey;
    }

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

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "ToscanelliEcommerce Purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return  PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
