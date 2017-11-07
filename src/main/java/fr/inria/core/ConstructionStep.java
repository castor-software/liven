package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class ConstructionStep extends AbstractStep {

    public ConstructionStep(Map<String, File> models, String name) throws IncorrectYAMLInformationException {
        super(models, name);
    }

}
