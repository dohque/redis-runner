package ru.pilin.redis.runner.core;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import ru.pilin.redis.runner.core.RedisServerRunner;

public class RedisServerRunnerTest {

    @Test
    public void shouldStartAndStopRedisServerFromPath() throws Exception {
        RedisServerRunner redisServerRunner = new RedisServerRunner();
        redisServerRunner.start();
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.connect();
            try {
                jedis.set("test:redis:passed", "true");
                assertEquals("true", jedis.get("test:redis:passed"));
            } finally {
                jedis.disconnect();
            }
        } finally {
            redisServerRunner.stop();
        }
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.connect();
            try {
                jedis.dbSize();
            } finally {
                jedis.disconnect();
            }
            fail("Redis was not stopped");
        } catch (Exception e) {
        }
    }

}
