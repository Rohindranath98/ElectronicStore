package com.lcwd.electronic.store.entites;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;
    private int totalPrice;
    //mapping
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;
}
