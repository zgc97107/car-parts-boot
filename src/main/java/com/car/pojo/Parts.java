package com.car.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Parts {
    private Integer id;

    private String partname;

    private Integer dealerId;

    private String image;

    private BigDecimal price;

    private Integer typeId;

    private Integer stock;

    private Integer sales;

    private Integer partState;

    private Type type;

    private User user;
}
