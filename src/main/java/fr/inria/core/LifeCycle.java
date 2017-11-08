package fr.inria.core;

import fr.inria.core.YamlParsing.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class LifeCycle {

    ProjectInfo projectInfo;
    Map<String,Step> steps = new HashMap<>();

    public boolean containsCycle(String cycle) {
        return steps.containsKey(cycle);
    }

    public void listCycles() {

        for(String cycle: steps.keySet()) {
            Step step = steps.get(cycle);
            System.out.println("\t* " + cycle + " (" + step.getType() + ")");
            if(step instanceof Cycle) {
                for (Step s : ((Cycle) step).steps) {
                    System.out.println("\t\t- " + s.getName() + " (" + s.getType() + ")");
                }
            }
        }
    }

    public void runCycle(String cycle) {
        if(!containsCycle(cycle)) return;
        Step step = steps.get(cycle);

        System.out.println( "------------------------------------------------------------------------" );
        System.out.println( "Running " + step.getName() );
        System.out.println( "------------------------------------------------------------------------" );

        step.run(projectInfo.original);
    }


    public LifeCycle() {}

    void build(CyclesInfo info) throws IncorrectYAMLInformationException {
        for(StepInfo s :info.steps) {
            if(PluginRegistry.registry.containsKey(s.type)) {
                Class<? extends AbstractStep> plugin = PluginRegistry.registry.get(s.type);
                try {
                    Step step;
                        java.lang.reflect.Constructor<? extends AbstractStep> c = plugin.getConstructor(Map.class, String.class);
                        step = c.newInstance(s.conf, s.name);
                    if(step != null) {
                        if(steps.containsKey(step.getName())) {
                            throw new IncorrectYAMLInformationException("Conflicting steps names");
                        }
                        steps.put(step.getName(), step);
                    } else {
                        throw new IncorrectYAMLInformationException("Problem loading " + s.name + "(" + s.type + ")");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IncorrectYAMLInformationException("YAML parsing failed");
                }
            } else {
                throw new IncorrectYAMLInformationException("Unable to load step '" + s.type + "'");
            }
        }
        for(CycleInfo c : info.cycles) {
            String name = c.name;
            if(steps.containsKey(name)) {
                throw new IncorrectYAMLInformationException("Conflicting steps names (A previously defined step or cycle has the same name.)");
            }
            Cycle cycle = new Cycle(name);
            for(String st : c.steps) {
                if (steps.containsKey(st)) {
                    Step myStep = steps.get(st);
                    cycle.addStep(myStep);
                } else {
                    throw new IncorrectYAMLInformationException("Cycle '" + c.name + "' referenced undefined step '" + st + "'");
                }
            }
            steps.put(name,cycle);
        }
        projectInfo = info.project;
    }

    public void parseYaml(File cycle) throws FileNotFoundException, IncorrectYAMLInformationException {
        Constructor constructor = new Constructor(CyclesInfo.class);
        TypeDescription cycleDescription = new TypeDescription(CyclesInfo.class);
        cycleDescription.addPropertyParameters("steps", StepInfo.class);
        cycleDescription.addPropertyParameters("cycles", CycleInfo.class);
        cycleDescription.addPropertyParameters("project", ProjectInfo.class);
        constructor.addTypeDescription(cycleDescription);
        Yaml yaml = new Yaml(constructor);
        CyclesInfo cyclesInfo = (CyclesInfo) yaml.load(new FileInputStream(cycle));
        build(cyclesInfo);
    }
}
