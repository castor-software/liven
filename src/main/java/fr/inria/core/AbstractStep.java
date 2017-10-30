package fr.inria.core;

import java.io.File;

public abstract class AbstractStep implements Step {
    String name;
    public String getName() {
        return name;
    }
    public abstract String getType();

    public AbstractStep(File model, String name) {
        this.name = name;
    }
}
