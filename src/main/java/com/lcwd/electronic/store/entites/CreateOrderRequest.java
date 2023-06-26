package com.lcwd.electronic.store.entites;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
    @NotBlank(message ="Cart id is required")
    private String cartId;
    @NotBlank(message = "user id is required")
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOT-PAID";
    @NotBlank(message ="address is required")
    private String billingAddress;
    @NotBlank(message = "phone no is required")
    private String billingPhone;
    @NotBlank(message = "Billing name is required")
    private String billingName;

}
