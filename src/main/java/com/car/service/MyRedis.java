package com.car.service;

public interface MyRedis {

    //存储
    public void set(String key, String value);
    //存储的时候就设置过期时间
    public void set(String key, String value, int timeout);
    //取值
    public String get(String key);
    //删除
    public void del(String key);
    //递增
    public Long incr(String key);
    //递减
    public Long decr(String key);
    //过期时间
    public void expire(String key, int timeout);
    //得到剩余时间
    public Long getExp(String key);

    //队列的存与取:先进先去
    public void lpush(String key, Object value);

    public Object rpop(String key);

    public void hset(String key, String item, String value);

    public String hget(String key, String item);

    Boolean exist(String key);

}
