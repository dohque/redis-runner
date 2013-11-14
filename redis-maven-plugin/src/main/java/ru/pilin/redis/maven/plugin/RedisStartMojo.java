package ru.pilin.redis.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import ru.pilin.redis.runner.core.RedisServerConfiguration;
import ru.pilin.redis.runner.core.RedisServerRunner;

/**
 * Start redis server server in background.
 */

@Mojo(name="start")
@Execute(goal="start", phase=LifecyclePhase.PRE_INTEGRATION_TEST)
public class RedisStartMojo extends AbstractMojo {

    @Parameter(defaultValue="/usr/local/bin/redis-server")
    private String redisServerCmd;

    @Parameter(defaultValue="src/redis/redis-test.conf")
    private File redisConf;

    @Parameter(defaultValue="localhost")
    private String hostname = "localhost";

    @Parameter(defaultValue="6379")
    private int port = 6379;

    @SuppressWarnings("unchecked")
    public void execute() {
        getLog().info("Starting redis-server.");
        RedisServerConfiguration configuration = new RedisServerConfiguration(
             redisServerCmd,
             redisConf,
             hostname,
             port
             );
        RedisServerRunner runner = new RedisServerRunner(configuration);
        runner.start();
        getPluginContext().put("ru.pilin.redis.server.runner", runner);
    }

}
