package fr.inria.core;

import java.io.File;

public class StepInfo {
    String name;
    String type;
    File model;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModel(File model) {
        this.model = model;
    }
}
