package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entites.CartItem;
import com.lcwd.electronic.store.entites.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items=new ArrayList<>();
}

