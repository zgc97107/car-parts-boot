package com.car.action;

import com.car.pojo.TableData;
import com.car.pojo.Type;
import com.car.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("type.html")
public class TypeAction {
    @Autowired
    private TypeService typeService;

    @RequestMapping(params = "act=type")
    public String type(ModelMap map){
        return "type";
    }

    @RequestMapping(params = "act=typesPage")
    @ResponseBody
    public TableData typesPage(String typename, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<Type> typesPage = typeService.getList(typename,pageNum,pageSize);
        data.setCode(0);
        data.setCount(typesPage.getTotal());
        data.setData(typesPage.getList());
        return data;
    }

    @RequestMapping(params = "act=upState")
    @ResponseBody
    public Map<String, Object> upState(Integer id,Integer typeState) {
        Map<String, Object> result = new HashMap<>();
        Type record=new Type();
        try {
            if(null!=id&&null!=typeState){
                record.setId(id);
                if(typeState.equals(1)){
                    record.setTypeState(0);
                }else{
                    record.setTypeState(1);
                }
                typeService.updateByPrimaryKeySelective(record);

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
    public Map<String, Object> edit(Type type) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(type.getId()==null){
                typeService.insert(type);
            }else{
                typeService.updateByPrimaryKeySelective(type);
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
    public Map<String, Object> del(int[] ids) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> idList=new ArrayList<>();
        try {
            if(ids!=null){
                for (int id : ids) {
                    idList.add(id);
                }
                typeService.delTypes(idList);
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
