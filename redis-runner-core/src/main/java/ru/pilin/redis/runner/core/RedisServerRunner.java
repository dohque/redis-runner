package ru.pilin.redis.runner.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisServerRunner {

    private final Logger log = LoggerFactory.getLogger(RedisServerRunner.class);

    private final RedisServerConfiguration configuration;

    private RedisServerProcess redisServerProcess;

    private String redisServerCmd = "/usr/local/bin/redis-server";

    @Deprecated
    public RedisServerRunner(String hostname, int port) {
        super();
        String redisServerCmdProp = System.getProperty("redis.server.cmd");
        if (redisServerCmdProp != null) {
            redisServerCmd = redisServerCmdProp;
        }
        configuration = new RedisServerConfiguration(redisServerCmd, hostname, port);
    }

    public RedisServerRunner(RedisServerConfiguration configuration) {
        this.configuration = configuration;
    }

    public RedisServerRunner() {
        this("localhost", 6379);
    }

    public RedisServerProcess start() {
        log.trace(".start()");
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(new String[] {
                configuration.getRedisServerCmd(),
                configuration.getRedisConf().getAbsolutePath() });
//            TODO better way to wait for redis to start
            Jedis jedis = new Jedis(
                configuration.getHostname(),
                configuration.getPort());
            int tries = 0;
            while (true) {
                tries ++;
                try {
                    jedis.connect();
                    break;
                } catch (JedisConnectionException e) {
                    if (tries < 10) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        throw new RedisServerExcpetion(e.getMessage(), e);
                    }
                }
            }
            // TODO bind output stream to log
            redisServerProcess = new RedisServerProcess(process, jedis);
            log.debug("Redis Server is up.");
            return redisServerProcess;
        } catch (IOException e) {
            throw new RedisServerExcpetion(e.getMessage(), e);
        }
    }

    /**
     * @deprecated use {@link #shutdown()}
     */
    @Deprecated
    public void stop() {
        shutdown();
    }

    /**
     * @deprecated use {@link RedisServerProcess#shutdown()}
     */
    @Deprecated
    public void shutdown() {
        redisServerProcess.shutdown();
    }

    public void setRedisServerCmd(String redisServerCmd) {
        this.redisServerCmd = redisServerCmd;
    }
}
