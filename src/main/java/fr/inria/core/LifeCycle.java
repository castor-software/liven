package fr.inria.core;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class LifeCycle {
    public LifeCycle() {}

    public void parseYaml(File cycle) throws FileNotFoundException {
        Constructor constructor = new Constructor(CyclesInfo.class);
        TypeDescription cycleDescription = new TypeDescription(CyclesInfo.class);
        cycleDescription.addPropertyParameters("steps", StepInfo.class);
        cycleDescription.addPropertyParameters("cycles", CycleInfo.class);
        constructor.addTypeDescription(cycleDescription);
        Yaml yaml = new Yaml(constructor);
        CyclesInfo cyclesInfo = (CyclesInfo) yaml.load(new FileInputStream(cycle));
    }
}
