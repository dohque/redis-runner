package ru.pilin.redis.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import ru.pilin.redis.runner.core.RedisServerRunner;

/**
 * Start redis server server in background.
 */

@Mojo(name="start")
@Execute(goal="start", phase=LifecyclePhase.PRE_INTEGRATION_TEST)
public class RedisStartMojo extends AbstractMojo {

    @Parameter(defaultValue="localhost")
    private String hostname = "localhost";

    @Parameter(defaultValue="6379")
    private int port = 6379;

    @SuppressWarnings("unchecked")
    public void execute() {
        getLog().info("Starting redis-server.");
        RedisServerRunner runner = new RedisServerRunner(hostname, port);
        runner.start();
        getPluginContext().put("ru.pilin.redis.server.runner", runner);
    }

}
