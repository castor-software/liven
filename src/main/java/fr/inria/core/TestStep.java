package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class TestStep extends AbstractStep {
    public TestStep(Map<String, File> models, String name) throws IncorrectYAMLInformationException {
        super(models, name);
    }
}
