package fr.inria.core.YamlParsing;

import java.util.List;

public class CycleInfo {
    public String name;
    public List<String> steps;

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
