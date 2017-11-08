package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.util.Map;

public class GenericConstruction extends ConstructionStep {
    Map<String, String> conf;
    String cmd;
    boolean isObliviousToPreviousFailure = false;

    public GenericConstruction(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        this.conf = conf;
        if(conf.containsKey("cmd")) {
            this.cmd = conf.get("cmd");
        } else {
            throw new IncorrectYAMLInformationException("extra does not contain a cmd field");
        }
        if(conf.containsKey("oblivious")) {
            isObliviousToPreviousFailure = conf.get("oblivious").equalsIgnoreCase("true");
        }
    }

    @Override
    public String getType() {
        return "generic";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return isObliviousToPreviousFailure;
    }

    @Override
    public Result run(File dir) {
        Result result;
        System.out.println("Run '" + cmd + "' in " + dir.getAbsolutePath());
        Runtime rt = Runtime.getRuntime();
        try {

            Process pr = rt.exec(cmd.split(" "), null, dir);
            StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream());
            StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream());
            errorGobbler.start();
            outputGobbler.start();
            int status = pr.waitFor();
            result = new Result(status, errorGobbler.getOutput());

        } catch (Exception e) {
            result = new Result(-1, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
