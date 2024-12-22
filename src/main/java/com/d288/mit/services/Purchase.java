package com.d288.mit.services;

import com.d288.mit.entities.Cart;
import com.d288.mit.entities.CartItem;
import com.d288.mit.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
