package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entites.Cart;
import com.lcwd.electronic.store.entites.Product;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;



}
