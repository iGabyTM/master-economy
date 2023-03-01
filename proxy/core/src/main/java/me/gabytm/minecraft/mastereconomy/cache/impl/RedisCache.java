package me.gabytm.minecraft.mastereconomy.cache.impl;

import me.gabytm.minecraft.mastereconomy.cache.Cache;
import redis.clients.jedis.JedisPooled;

public class RedisCache extends Cache {

    private JedisPooled jedis = new JedisPooled();

}
