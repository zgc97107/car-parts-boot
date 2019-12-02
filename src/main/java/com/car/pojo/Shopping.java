package com.car.pojo;

import lombok.Data;

@Data
public class Shopping {
    private Integer id;

    private Integer userId;

    private Integer partId;

    private Integer num;

    private Parts parts;
}
