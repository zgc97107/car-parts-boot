package com.car.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private Integer id;

    private String menuname;

    private Integer upmenuid;

    private String menupath;

    private Integer menuState;

    /**
     * 保存二级菜单的属性
     */
    private List<Menu> seconds = new ArrayList<>();

}
