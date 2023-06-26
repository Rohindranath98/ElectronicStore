package com.lcwd.electronic.store.entites;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String orderId;
    //Pending,Dispatched,Delivered
    //enum
    private String orderStatus;
    //Status PAID or NOTPAID
    //enum
    //boolean false=not-paid true=paid
    private String paymentStatus;
    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "order",fetch =FetchType.EAGER,cascade =CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();


}
