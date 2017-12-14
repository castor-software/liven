package fr.inria.core;

import fr.inria.core.YamlParsing.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Project {

    ProjectInfo projectInfo;
    Map<String,Step> steps = new HashMap<>();
    File rootDir;


    static Project instance;


    public Project() {
        if(instance == null)
            instance = this;
    }


    public static Project getInstance() {
        return instance;
    }

    public File getOriginalRoot() {
        return projectInfo.original;
    }

    public File getTmpRoot() {
        return FileUtils.getDirOrCreate(getDiversityRoot(),"tmp");
    }

    public void createTmp() {
        File tmpDir = new File(getDiversityRoot(),"tmp");
        if(tmpDir.exists()) tmpDir.delete();
        File original = getOriginalRoot();
        try {
            org.apache.commons.io.FileUtils.copyDirectory(original,tmpDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getVariantRoot() {
        return FileUtils.getDirOrCreate(getDiversityRoot(),"variants");
    }

    public File getDiversityRoot() {
        return FileUtils.getDirOrCreate(rootDir,"diversity");
    }

    public File getPluginDir(TransformationStep t) {
        return FileUtils.getDirOrCreate(getDiversityRoot(),t.getName());
    }


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

    public Cycle getCycle(String cycle) {
        return (Cycle) steps.get(cycle);
    }


    public Step getStep(String step) {
        return steps.get(step);
    }

    public List<TransformationStep> getTransformations(Cycle cycle) {
        List<TransformationStep> transformations = new ArrayList<>();
        for(Step s: cycle.getChildren()) {
            if(s instanceof TransformationStep) transformations.add((TransformationStep) s);
        }
        return transformations;
    }

    public List<TransformationStep> getTransformations() {
        List<TransformationStep> transformations = new ArrayList<>();
        for(String cycle: steps.keySet()) {
            Step step = steps.get(cycle);
            if(step instanceof TransformationStep) transformations.add((TransformationStep) step);
            if(step instanceof Cycle) {
                Cycle c = (Cycle) step;
                for(Step s: c.getChildren()) {
                    if(s instanceof TransformationStep) transformations.add((TransformationStep) s);
                }
            }
        }
        return transformations;
    }

    public void runCycle(String cycle) {
        if(!containsCycle(cycle)) return;
        Step step = steps.get(cycle);

        System.out.println( "------------------------------------------------------------------------" );
        System.out.println( "Running " + step.getName() );
        System.out.println( "------------------------------------------------------------------------" );

        step.run(projectInfo.original);
    }

    /*public void generateRoadMap(String cycle) {

        File explorationDir = projectInfo.exploration;

        if(!explorationDir.exists()) explorationDir.mkdirs();
        if(!explorationDir.isDirectory()) {
            System.err.println("The file " + explorationDir.getPath() + "should be a dir.");
            return;
        }

        Step step = steps.get(cycle);
        List<Step> children = step.getChildren();
        for(Step c : children) {
            if(c instanceof TransformationStep) {
                TransformationStep t = (TransformationStep) c;
                File tDir = new File(explorationDir, t.name);
                tDir.mkdirs();
                File roadMap;
                if(t.conf.containsKey("roadmap")) {
                    roadMap = new File(tDir, t.conf.get("roadmap"));
                } else {
                    roadMap = new File(tDir, "roadmap");
                    t.conf.put("roadmap", "roadmap");
                }

                //t.generateExplorationRoadMap(roadMap);
            }
        }
    }*/

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
                    throw new IncorrectYAMLInformationException("YAML parsing failed for step " + s.name + "(" + s.type + ")");
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
        rootDir = cycle.getParentFile();
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
