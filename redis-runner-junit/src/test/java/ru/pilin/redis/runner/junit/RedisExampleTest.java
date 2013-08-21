package ru.pilin.redis.runner.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import redis.clients.jedis.Jedis;
import ru.pilin.redis.runner.junit.RedisServerJunitRunner;

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
