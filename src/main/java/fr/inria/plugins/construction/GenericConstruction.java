package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.IncorrectYAMLInformationException;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GenericConstruction extends ConstructionStep {
    Map<String, File> models;
    String cmd;
    public GenericConstruction(Map<String, File> models, String name) throws IncorrectYAMLInformationException {
        super(models, name);
        this.models = models;
    }
    public GenericConstruction(Map<String, File> models, String name, Map<String, String> extra) throws IncorrectYAMLInformationException {
        super(models, name);
        this.models = models;
        if(extra.containsKey("cmd")) {
            this.cmd = extra.get("cmd");
        } else {
            throw new IncorrectYAMLInformationException("extra does not contain a cmd field");
        }
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
