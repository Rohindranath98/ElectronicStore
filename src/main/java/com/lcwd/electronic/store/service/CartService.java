package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import org.springframework.stereotype.Service;


public interface CartService {
    //add items to the cart
    //case1: If cart is not available for user then we will create the then add item
    //case2: If cart available then add the item to cart
   CartDto addItemToCart(String userId, AddItemToCartRequest request);
   void removeItemFromCart(String userId,int cartItemId);
   void clearCart(String userId);
    CartDto getCartByUser(String userId);
}
