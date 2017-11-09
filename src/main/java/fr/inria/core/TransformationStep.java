package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.io.File;
import java.util.Map;

public abstract class TransformationStep extends AbstractStep {
    public TransformationStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    public abstract void generateExplorationRoadMap(File roadMap, Map<String, String> models);

    public abstract void explore(File roadMap, File explorationResults, Map<String, String> models);

    public abstract void transform(File explorationResults, Map<String, String> models);
}
