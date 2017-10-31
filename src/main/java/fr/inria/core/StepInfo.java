package fr.inria.core;

import java.io.File;

public class StepInfo {
    String name;
    String type;
    File model;
    String extra;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModel(File model) {
        this.model = model;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

}
