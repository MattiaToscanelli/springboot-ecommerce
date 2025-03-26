package ch.toscanelli.ecommerce.dto;

import ch.toscanelli.ecommerce.entity.Address;
import ch.toscanelli.ecommerce.entity.Customer;
import ch.toscanelli.ecommerce.entity.Order;
import ch.toscanelli.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
