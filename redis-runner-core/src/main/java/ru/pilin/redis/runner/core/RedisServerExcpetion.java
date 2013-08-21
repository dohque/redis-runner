package ru.pilin.redis.runner.core;

@SuppressWarnings("serial")
public class RedisServerExcpetion extends RuntimeException {

    public RedisServerExcpetion() {
        super();
    }

    public RedisServerExcpetion(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RedisServerExcpetion(String message) {
        super(message);
    }

    public RedisServerExcpetion(Throwable throwable) {
        super(throwable);
    }
}
