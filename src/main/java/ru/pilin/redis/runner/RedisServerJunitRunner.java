package ru.pilin.redis.runner;

import java.util.List;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class RedisServerJunitRunner extends BlockJUnit4ClassRunner {

    public RedisServerJunitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> testRules = super.getTestRules(target);
        testRules.add(new ExternalResource() {
            private RedisServerRunner runner = new RedisServerRunner();

            @Override
            protected void before() throws Throwable {
                runner.start();
            }

            @Override
            protected void after() {
                runner.stop();
            }
        });
        return testRules;
    }
}
