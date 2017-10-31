package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DockerCompose extends ConstructionStep {

    File dockerComposeFile;

    @Override
    public String getType() {
        return "docker-compose";
    }

    public DockerCompose(File dockerComposeFile, String name) {
        super(dockerComposeFile, name);
        this.dockerComposeFile = dockerComposeFile;
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
