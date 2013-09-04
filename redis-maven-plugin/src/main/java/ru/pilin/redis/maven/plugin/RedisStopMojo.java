package ru.pilin.redis.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import ru.pilin.redis.runner.core.RedisServerRunner;

/**
 * Stop redis server mojo.
 */
@Mojo(name="stop")
@Execute(goal="stop", phase=LifecyclePhase.POST_INTEGRATION_TEST)
public class RedisStopMojo extends AbstractMojo {

    @Override
    public void execute() {
        getLog().info("Shutting down redis server.");
        RedisServerRunner runner = (RedisServerRunner) getPluginContext()
            .get("ru.pilin.redis.server.runner");
        runner.shutdown();
    }
}
