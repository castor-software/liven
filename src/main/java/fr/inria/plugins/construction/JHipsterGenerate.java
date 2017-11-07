package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.IncorrectYAMLInformationException;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JHipsterGenerate extends ConstructionStep {

    File yoRc;

    @Override
    public String getType() {
        return "jhipster";
    }

    public JHipsterGenerate(Map<String, File> models, String name) throws IncorrectYAMLInformationException {
        super(models, name);
        if(models.containsKey("json")) {
            this.yoRc = models.get("json");
        } else {
            throw new IncorrectYAMLInformationException("Missing Dockerfile in models");
        }
    }

    @Override
    public void run(File dir) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] { "yo", "jhipster" }, null, dir);
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