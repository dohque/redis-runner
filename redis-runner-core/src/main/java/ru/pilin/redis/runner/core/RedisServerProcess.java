package ru.pilin.redis.runner.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public final class RedisServerProcess {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Process process;

    private final Jedis jedis;

    RedisServerProcess(Process process, Jedis jedis) {
        super();
        this.process = process;
        this.jedis = jedis;
    }

    public void shutdown() {
        log.trace(".shutdown()");
        Thread waitForRedisStop = new Thread(new Runnable() {
            public void run() {
                try {
                    process.waitFor();
                } catch (InterruptedException ignore) {
                }
            }
        });
        try {
            jedis.shutdown();
            waitForRedisStop.start();
            waitForRedisStop.join(500);
        } catch (InterruptedException e) {
            log.debug(e.getMessage(), e);
//            throw new RedisServerExcpetion("Redis shutdown interrupted", e);
        } finally {
            if (waitForRedisStop.isAlive()) {
                waitForRedisStop.interrupt();
                process.destroy();
            }
        }
        log.debug("Redis Server is down.");
    }
}
