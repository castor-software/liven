package fr.inria.plugins;

import fr.inria.core.ConstructionStep;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GenericConstruction extends ConstructionStep {
    File model;
    String cmd;
    public GenericConstruction(File model, String name) {
        super(model, name);
        this.model = model;
    }
    public GenericConstruction(File model, String name, String extra) {
        super(model, name);
        this.model = model;
        this.cmd = extra;
    }

    @Override
    public String getType() {
        return "generic";
    }

    @Override
    public void run(File dir) {
        System.out.println("Run '" + cmd + "' in " + dir.getAbsolutePath());
        /*Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }*/
        Runtime rt = Runtime.getRuntime();
        try {

            Process pr = rt.exec(cmd.split(" "), null, dir);
            StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream());
            StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream());
            errorGobbler.start();
            outputGobbler.start();
            pr.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
