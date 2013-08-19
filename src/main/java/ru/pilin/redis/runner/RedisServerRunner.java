package ru.pilin.redis.runner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

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
            process = runtime.exec("/usr/local/bin/redis-server -");
            InputStream input = getClass().getResourceAsStream("/redis-test.conf");
            OutputStream output = process.getOutputStream();
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
//            TODO better way to wait for redis to start
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
        } catch (IOException e) {
            throw new RedisServerExcpetion(e.getMessage(), e);
        }
    }

    // TODO: rename to shutdown
    public void stop() {
        log.trace(".stop()");
        try {
            String result = jedis.shutdown();
            log.debug("Shutdown returned: {}", result);
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RedisServerExcpetion(e.getMessage(), e);
        }
    }
}
