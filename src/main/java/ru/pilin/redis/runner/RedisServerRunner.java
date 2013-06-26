package ru.pilin.redis.runner;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisServerRunner {

    private Logger log = LoggerFactory.getLogger(RedisServerRunner.class);

    private Runtime runtime = Runtime.getRuntime();

    private final Jedis jedis;

    private Process process;

    public RedisServerRunner(String hostname, int port) {
        super();
        jedis = new Jedis(hostname, port);
    }

    public RedisServerRunner() {
        this("localhost", 6379);
    }

    public void start() {
        log.trace(".start()");
        try {
            process = runtime.exec("/usr/local/bin/redis-server");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // TODO: rename to shutdown
    public void stop() {
        log.trace(".stop()");
        try {
            jedis.shutdown();
            process.waitFor();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
