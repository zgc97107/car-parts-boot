package com.car.action;

import com.car.pojo.Menu;
import com.car.pojo.TableData;
import com.car.service.MenuService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("menu.html")
public class MenuAction {
    @Autowired
    private MenuService menuService;

    @RequestMapping(params = "act=menusPage")
    @ResponseBody
    public TableData menusPage(String menuname, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<Menu> menusPage = menuService.getList(menuname,pageNum,pageSize);
        data.setCode(0);
        data.setCount(menusPage.getTotal());
        data.setData(menusPage.getList());
        return data;
    }

    @RequestMapping(params = "act=upState")
    @ResponseBody
    public Map<String, Object> upState(Integer id,Integer menuState) {
        Map<String, Object> result = new HashMap<>();
        Menu record=new Menu();
        try {
            if(null!=id&&null!=menuState){
                record.setId(id);
                if(menuState.equals(1)){
                    record.setMenuState(0);
                }else{
                    record.setMenuState(1);
                }
                menuService.updateByPrimaryKeySelective(record);
            }
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(Menu menu, Integer umEdit) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(umEdit!=null){
                menu.setUpmenuid(umEdit);
            }
            if(menu.getId()==null){
                menuService.insert(menu);
            }else{
                menuService.updateByPrimaryKeySelective(menu);
            }
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=del")
    @ResponseBody
    public Map<String, Object> del(Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(id!=null){
                menuService.deleteByPrimaryKey(id);
                result.put("status",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }


}
