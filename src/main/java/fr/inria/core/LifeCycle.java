package fr.inria.core;

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
    Set<Step> steps = new HashSet<>();
    Map<String,List<Step>> cycles = new HashMap<>();

    public boolean containsCycle(String cycle) {
        return cycles.containsKey(cycle);
    }

    public void listCycles() {

        for(String cycle: cycles.keySet()) {
            System.out.println("\t* " + cycle + ":");
            for(Step s : cycles.get(cycle)) {
                System.out.println("\t\t- " + s.getName() + " (" + s.getType() +")");
            }
        }
    }

    public void runCycle(String cycle) {
        if(!containsCycle(cycle)) return;

        for(Step s : cycles.get(cycle)) {

            System.out.println( "------------------------------------------------------------------------" );
            System.out.println("Running " + s.getName());
            System.out.println( "------------------------------------------------------------------------" );

            s.run(projectInfo.original);
        }
    }


    public LifeCycle() {}

    void build(CyclesInfo info) {
        for(StepInfo s :info.steps) {
            if(PluginRegistry.registry.containsKey(s.type)) {
                Class<? extends AbstractStep> plugin = PluginRegistry.registry.get(s.type);
                try {
                    Step step;
                    if(s.extra != null) {
                        java.lang.reflect.Constructor<? extends AbstractStep> c = plugin.getConstructor(Map.class, String.class, Map.class);

                        step = c.newInstance(s.models, s.name, s.extra);
                    } else {
                        java.lang.reflect.Constructor<? extends AbstractStep> c = plugin.getConstructor(Map.class, String.class);
                        step = c.newInstance(s.models, s.name);
                    }
                    if(step != null) {
                        steps.add(step);
                    } else {
                        System.err.println("Problem loading " + s.name + "(" + s.type + ")");
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Unable to load step '" + s.type + "'");
            }
        }
        for(CycleInfo c : info.cycles) {
            String name = c.name;
            List<Step> l = new ArrayList<>();
            for(String st : c.steps) {
                if (steps.stream().filter(s -> s.getName().equals(st)).count() == 1) {
                    Step myStep = steps.stream().filter(s -> s.getName().equals(st)).findFirst().get();
                    l.add(myStep);
                } else {
                    System.err.println("Cycle '" + c.name + "' referenced undefined step '" + st + "'");
                }
            }
            cycles.put(name,l);
        }
        projectInfo = info.project;
    }

    public void parseYaml(File cycle) throws FileNotFoundException {
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
