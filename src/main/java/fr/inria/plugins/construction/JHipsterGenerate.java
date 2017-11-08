package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.util.Map;

public class JHipsterGenerate extends ConstructionStep {

    File yoRc;

    @Override
    public String getType() {
        return "jhipster";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    public JHipsterGenerate(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        if(conf.containsKey("json")) {
            this.yoRc = new File(conf.get("json"));
        } else {
            throw new IncorrectYAMLInformationException("Missing Dockerfile in models");
        }
    }

    @Override
    public Result run(File dir) {
        Result result;
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] { "yo", "jhipster" }, null, dir);
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