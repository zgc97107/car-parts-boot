package com.car.action;

import com.car.pojo.Role;
import com.car.pojo.TableData;
import com.car.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role.html")
public class RoleAction {
    @Autowired
    private RoleService roleService;

    @RequestMapping(params = "act=rolesPage")
    @ResponseBody
    public TableData rolesPage(String rolename, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<Role> rolesPage = roleService.getList(rolename,pageNum,pageSize);
        data.setCode(0);
        data.setCount(rolesPage.getTotal());
        data.setData(rolesPage.getList());
        return data;
    }

    @RequestMapping(params = "act=upState")
    @ResponseBody
    public Map<String, Object> upState(Integer id,Integer roleState) {
        Map<String, Object> result = new HashMap<>();
        Role record=new Role();
        try {
            if(null!=id&&null!=roleState){
                record.setId(id);
                if(roleState.equals(1)){
                    record.setRoleState(0);
                }else{
                    record.setRoleState(1);
                }
                roleService.updateByPrimaryKeySelective(record);
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
    public Map<String, Object> edit(Role role, int[] menuid) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(role.getId()==null){
                roleService.insertSelective(role,menuid);
            }else{
                roleService.updateByPrimaryKey(role,menuid);
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
                roleService.delRoles(idList);
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
