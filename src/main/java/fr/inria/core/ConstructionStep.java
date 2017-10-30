package fr.inria.core;

import java.io.File;

public abstract class ConstructionStep extends AbstractStep {

    public ConstructionStep(File model, String name) {
        super(model, name);
    }

}
