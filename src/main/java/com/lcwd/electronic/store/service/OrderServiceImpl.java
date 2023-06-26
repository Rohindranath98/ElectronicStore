package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entites.*;
import com.lcwd.electronic.store.exception.BadApiRequestException;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service

public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId=orderDto.getUserId();
        String cartId= orderDto.getCartId();
        //fetch user
       User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given id"));
       //fetch cart
             Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart with given cartId not found"));
              List<CartItem>cartItems=cart.getItems();
              if(cartItems.size() <=0){
                  throw new BadApiRequestException("Invalid number of items in cart");
              }
     Order order= Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getPaymentStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
              //order items ,amount
        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems= cartItems.stream().map(cartItem ->{
            //cartItem-> orderItem
        OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product((cartItem.getProduct()))
                    .totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
        orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
        order.setOrderAmount(orderAmount.get());
           return orderItem;

        }).collect(Collectors.toList());
     order.setOrderItems(orderItems);
     cart.getItems().clear();
     cartRepository.save(cart);
     Order savedOrder= orderRepository.save(order);
        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String OrderId) {
       Order order= orderRepository.findById(OrderId).orElseThrow(()-> new ResourceNotFoundException("order not found"));
       orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
         List<Order> orders =orderRepository.findByUser(user);
        List<OrderDto> orderDtos= orders.stream().map(order -> modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page=orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
