package fr.inria.core.YamlParsing;

import java.util.Set;

public class CyclesInfo {
    public Set<StepInfo> steps;

    public void setSteps(Set<StepInfo> steps) {
        this.steps = steps;
    }

    public Set<CycleInfo> cycles;

    public void setCycles(Set<CycleInfo> cycles) {
        this.cycles = cycles;
    }

    public ProjectInfo project;

    public void setProject(ProjectInfo project) {
        this.project = project;
    }


}
