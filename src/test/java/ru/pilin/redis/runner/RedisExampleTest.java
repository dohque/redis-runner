package ru.pilin.redis.runner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import redis.clients.jedis.Jedis;

@RunWith(RedisServerJunitRunner.class)
public class RedisExampleTest {

    @Test
    public void shouldDoSomeThingUsefulOnRedis() throws Exception {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.connect();
        try {
            assertNull(jedis.get("test:redis:passed"));
            jedis.set("test:redis:passed", "true");
            assertEquals("true", jedis.get("test:redis:passed"));
        } finally {
            jedis.disconnect();
        }
    }

}
