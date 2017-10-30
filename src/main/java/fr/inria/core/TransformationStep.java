package fr.inria.core;

import java.io.File;

public abstract class TransformationStep extends AbstractStep {
    public TransformationStep(File model, String name) {
        super(model, name);
    }
}
