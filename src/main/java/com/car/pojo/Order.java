package com.car.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private String id;

    private Integer customerId;

    private Integer dealerId;

    private Integer partId;

    private Integer num;

    private BigDecimal price;

    private Integer orderState;

    private BigDecimal allPrice;

    private User customer;

    private User dealer;

    private Parts parts;
}
