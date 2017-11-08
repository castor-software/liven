package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.util.Map;

public abstract class AbstractStep implements Step {
    String name;
    public String getName() {
        return name;
    }
    public abstract String getType();

    public AbstractStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        this.name = name;
    }
}
