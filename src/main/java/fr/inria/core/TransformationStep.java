package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class TransformationStep extends AbstractStep {
    public TransformationStep(File model, String name) {
        super(model, name);
    }

    public abstract void generateExplorationRoadMap();

    public abstract void explore(File roadMap, Map<String, File> models);

    public abstract void transform(File explorationResults, Map<String, File> models);
}
