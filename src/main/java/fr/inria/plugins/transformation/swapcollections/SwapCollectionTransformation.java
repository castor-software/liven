package fr.inria.plugins.transformation.swapcollections;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import fr.inria.core.TransformationStep;

import java.io.File;
import java.util.Map;

public class SwapCollectionTransformation extends TransformationStep {

    public SwapCollectionTransformation(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    @Override
    public void generateExplorationRoadMap() {

    }

    @Override
    public void explore(File roadMap, Map<String, File> models) {

    }

    @Override
    public void transform(File explorationResults, Map<String, File> models) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    @Override
    public Result run(File dir) {
        return null;
    }
}
