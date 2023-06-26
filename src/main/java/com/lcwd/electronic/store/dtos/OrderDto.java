package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entites.OrderItem;
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
@ToString
public class OrderDto {
    private String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOT-PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate=new Date();
    private Date deliveredDate;
    //private UserDto user;
    private List<OrderItemDto> orderItems=new ArrayList<>();
}
