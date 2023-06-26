package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entites.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    //createOrder
    OrderDto createOrder(CreateOrderRequest orderDto);
    //removeOrder
    void removeOrder(String OrderId);
    // get order by user
    List<OrderDto> getOrdersOfUser(String userId);

    //get order
    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
    //other method related to order
}
