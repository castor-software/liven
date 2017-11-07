package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.IncorrectYAMLInformationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class DockerCompose extends ConstructionStep {

    File dockerComposeFile;

    @Override
    public String getType() {
        return "docker-compose";
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
    public void run(File dir) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] { "docker-compose", dockerComposeFile.getAbsolutePath() }, null, dir);

            pr.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
