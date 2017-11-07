package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class TestStep extends AbstractStep {
    public TestStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }
}
