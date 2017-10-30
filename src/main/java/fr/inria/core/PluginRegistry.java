package fr.inria.core;

import fr.inria.plugins.*;

import java.util.HashMap;
import java.util.Map;

public class PluginRegistry {
    static Map<String, Class<? extends AbstractStep>> registry;

    static {
        registry = new HashMap<>();
        registry.put("maven-compile", MavenCompile.class);
        registry.put("maven-test", MavenTest.class);
        registry.put("jhipster", JHipsterGenerate.class);
        registry.put("docker", DockerBuild.class);
        registry.put("docker-compose", DockerCompose.class);
    }
}
