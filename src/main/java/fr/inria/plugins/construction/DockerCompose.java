package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.util.Map;

public class DockerCompose extends ConstructionStep {

    File dockerComposeFile;

    @Override
    public String getType() {
        return "docker-compose";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    public DockerCompose(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        if(conf.containsKey("yml")) {
            this.dockerComposeFile = new File(conf.get("yml"));
        } else {
            throw new IncorrectYAMLInformationException("Missing yml in models");
        }
    }

    @Override
    public Result run(File dir) {
        Result result;
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] { "docker-compose", dockerComposeFile.getAbsolutePath() }, null, dir);
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
