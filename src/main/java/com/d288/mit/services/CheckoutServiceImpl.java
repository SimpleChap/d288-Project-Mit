package com.d288.mit.services;

import com.d288.mit.dao.CartRepository;
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

    private final CartRepository cartRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        if (purchase.getCart() == null || purchase.getCartItems() == null || purchase.getCartItems().isEmpty()) {
            return new PurchaseResponse("Cart is Empty");
        }

        // Retrieve the cart
        Cart cart = purchase.getCart();

        // Set status to ordered
        cart.setStatus(StatusType.ordered);

        // Generate a tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        //Populate cart
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        //populate customer with cart
        Customer customer = purchase.getCustomer();
        cart.setCustomer(customer);

        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // To generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
