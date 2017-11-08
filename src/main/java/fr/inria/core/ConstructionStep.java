package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.util.Map;

public abstract class ConstructionStep extends AbstractStep {

    public ConstructionStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

}
