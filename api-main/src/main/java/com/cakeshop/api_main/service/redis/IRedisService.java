package com.cakeshop.api_main.service.redis;

public interface IRedisService {

    <T> T getObject(String key, Class<T> clazz);

    <T> void setObject(String key, T value, Integer timeout);

    void deleteObject(String key);
}
