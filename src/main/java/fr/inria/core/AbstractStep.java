package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class AbstractStep implements Step {
    String name;
    public String getName() {
        return name;
    }
    public abstract String getType();

    public AbstractStep(Map<String, File> models, String name) throws IncorrectYAMLInformationException {
        this.name = name;
    }
}
