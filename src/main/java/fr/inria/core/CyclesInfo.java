package fr.inria.core;

import java.util.List;
import java.util.Set;

public class CyclesInfo {
    Set<StepInfo> steps;

    public void setSteps(Set<StepInfo> steps) {
        this.steps = steps;
    }

    Set<CycleInfo> cycles;

    public void setCycles(Set<CycleInfo> cycles) {
        this.cycles = cycles;
    }

    ProjectInfo project;

    public void setProject(ProjectInfo project) {
        this.project = project;
    }


}
