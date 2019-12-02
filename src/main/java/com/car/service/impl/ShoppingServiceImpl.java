package com.car.service.impl;

import com.car.common.utils.DayNumUtils;
import com.car.mapper.OrderMapper;
import com.car.mapper.PartsMapper;
import com.car.mapper.ShoppingMapper;
import com.car.pojo.Order;
import com.car.pojo.Parts;
import com.car.pojo.Shopping;
import com.car.pojo.ShoppingExample;
import com.car.service.ShoppingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private OrderMapper orderMapper;

    //手动事务
    @Autowired
    private DataSourceTransactionManager tx;

    @Override
    public long countByExample(ShoppingExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(ShoppingExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Shopping record) {
        return shoppingMapper.insert(record);
    }

    @Override
    public int insertSelective(Shopping record) {
        return 0;
    }

    @Override
    public List<Shopping> selectByExample(ShoppingExample example) {
        return null;
    }

    @Override
    public Shopping selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Shopping record, ShoppingExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Shopping record, ShoppingExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Shopping record) {
        return shoppingMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Shopping record) {
        return 0;
    }

    @Override
    public PageInfo<Shopping> getList(Integer dealerId, Integer userId, String partname, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("dealerId",dealerId);
        param.put("userId",userId);
        param.put("partname",partname);
        PageHelper.startPage(pageNum,pageSize);
        List<Shopping> marksList=shoppingMapper.getList(param);
        PageInfo<Shopping> pageInfo=new PageInfo<>(marksList);
        return pageInfo;
    }

    @Override
    public void delMarks(List<Integer> ids) {
        ShoppingExample shoppingExample=new ShoppingExample();
        ShoppingExample.Criteria criteria = shoppingExample.createCriteria();
        criteria.andIdIn(ids);
        shoppingMapper.deleteByExample(shoppingExample);
    }

    @Override
    public int accountMarks(LinkedList<Integer> ids,String[] idnums) {
        int i=1;
        //开启手动事务
        DefaultTransactionDefinition definition=new DefaultTransactionDefinition();
        TransactionStatus status = tx.getTransaction(definition);
        try {
            String timestamp = DayNumUtils.getTimestamp();
            //每个配件对应的数量 1-13 3-44 id-num
            List<Integer> nums=new LinkedList<>();
            Set<Integer> dealers=new HashSet<>();
            if(ids.size()==idnums.length){
                for (String s : idnums) {
                    int id=Integer.parseInt(s.substring(0,s.indexOf("-")));
                    int num=Integer.parseInt(s.substring(s.indexOf("-")+1));

                    Integer partId1 = shoppingMapper.selectByPrimaryKey(id).getPartId();
                    Integer dealerId1 = partsMapper.selectByPrimaryKey(partId1).getDealerId();
                    dealers.add(dealerId1);
                    if (dealers.size()>1) {
                        i=-1;
                        throw new Exception("请选择同一个卖家");
                    }else{
                        for (int i1 = 0; i1 < ids.size(); i1++) {
                            Integer integer = ids.get(i1);
                            if(id==integer){
                                Shopping shopping = shoppingMapper.selectByPrimaryKey(id);
                                Integer customerId = shopping.getUserId();
                                Integer partId = shopping.getPartId();
                                Parts parts = partsMapper.selectByPrimaryKey(partId);
                                if(num>parts.getStock()){//库存不足
                                    i=0;
                                    throw new Exception("系统错误，结算失败");
                                }else{
                                    nums.add(num);
                                }
                                //增加订单信息
                                Integer dealerId = parts.getDealerId();
                                BigDecimal price = parts.getPrice();
                                Order order=new Order();
                                order.setId(timestamp);
                                order.setCustomerId(customerId);
                                order.setDealerId(dealerId);
                                order.setPartId(partId);
                                order.setNum(num);
                                order.setPrice(price);
                                order.setOrderState(0);
                                orderMapper.insert(order);
                                //修改配件信息（减库存，加销量）
                                Integer stock = parts.getStock();
                                Integer sales = parts.getSales();
                                parts.setStock(stock-num);
                                parts.setSales(sales+num);
                                partsMapper.updateByPrimaryKeySelective(parts);
                                //删除购物车信息
                                shoppingMapper.deleteByPrimaryKey(id);
                            }
                        }
                    }
                }
            }
            tx.commit(status);
        } catch (Exception e) {
            tx.rollback(status);
        }
        return i;
    }
}
