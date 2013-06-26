package ru.pilin.redis.runner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisServerRunnerTest {

    @Test
    public void shouldStartAndStopRedisServerFromPath() throws Exception {
        RedisServerRunner redisServerRunner = new RedisServerRunner();
        redisServerRunner.start();
        JedisConnectionFactory connectionFactory;
        RedisTemplate<String, String> redis;

        connectionFactory = new JedisConnectionFactory();
        connectionFactory.setUsePool(true);
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.afterPropertiesSet();

        redis = new RedisTemplate<String, String>();
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setConnectionFactory(connectionFactory);
        redis.afterPropertiesSet();


        redis.opsForValue().set("test:redis:passed", "true");
        assertEquals("true", redis.opsForValue().get("test:redis:passed"));
        redisServerRunner.stop();
        // TODO check there is no redis
    }

}
