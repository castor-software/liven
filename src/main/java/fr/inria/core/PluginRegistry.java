package fr.inria.core;

import fr.inria.plugins.construction.*;
import fr.inria.plugins.transformation.SwapDockerBaseImageTransformation;
import fr.inria.plugins.transformation.swapcollections.SwapCollectionTransformation;

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
        registry.put("generic", GenericConstruction.class);
        registry.put("pit", PitTest.class);
        registry.put("cobertura", CoberturaTest.class);
        registry.put("html-report", HtmlReport.class);
        registry.put("swap-collections", SwapCollectionTransformation.class);
        registry.put("docker-base-image-swap", SwapDockerBaseImageTransformation.class);
    }
}
