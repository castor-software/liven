package fr.inria.core;

import java.io.File;
import java.util.Map;

public abstract class TestStep extends AbstractStep {
    public TestStep(File model, String name) {
        super(model, name);
    }
}
