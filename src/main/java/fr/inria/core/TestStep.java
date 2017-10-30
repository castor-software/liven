package fr.inria.core;

import java.io.File;

public abstract class TestStep extends AbstractStep {
    public TestStep(File model, String name) {
        super(model, name);
    }
}
