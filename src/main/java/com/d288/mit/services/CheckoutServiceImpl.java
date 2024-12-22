package com.d288.mit.services;

import com.d288.mit.dao.CustomerRepository;
import com.d288.mit.entities.Cart;
import com.d288.mit.entities.CartItem;
import com.d288.mit.entities.Customer;
import com.d288.mit.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;


    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // Retrieve the cart
        Cart cart = purchase.getCart();

        // Generate a tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Set status to ordered
        cart.setStatus(StatusType.ordered);

        //Populate cart
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        //populate customer with cart
        Customer customer = purchase.getCustomer();
        customer.add(cart);

        // save customer to database
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // To generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
