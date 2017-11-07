package fr.inria.core;

import java.io.File;
import java.util.Map;

public class StepInfo {
    String name;
    String type;
    Map<String, File> models;
    Map<String, String> extra;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModels(Map<String,File> models) {
        this.models = models;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

}
