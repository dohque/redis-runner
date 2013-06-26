package ru.pilin.redis.runner;

import static org.junit.Assert.*;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisServerRunnerTest {

    @Test
    public void shouldStartAndStopRedisServerFromPath() throws Exception {
        RedisServerRunner redisServerRunner = new RedisServerRunner();
        redisServerRunner.start();
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.connect();
        try {
            jedis.set("test:redis:passed", "true");
            assertEquals("true", jedis.get("test:redis:passed"));
        } finally {
            jedis.disconnect();
        }
        redisServerRunner.stop();
        try {
            jedis.dbSize();
            fail("Redis was not stopped");
        } catch (Exception e) {
        }
    }

}
