package me.gabytm.minecraft.mastereconomy.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import redis.clients.jedis.Protocol;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
@ConfigSerializable
public class RedisConfig {

    private String host = Protocol.DEFAULT_HOST;
    private int port = Protocol.DEFAULT_PORT;
    private int timeout = Protocol.DEFAULT_TIMEOUT;
    private String user;
    private String password;
    private boolean ssl = true;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSsl() {
        return ssl;
    }

}
