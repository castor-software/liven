package fr.inria.plugins;

import fr.inria.core.ConstructionStep;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.io.IOException;

public class JHipsterGenerate extends ConstructionStep {

    File yoRc;

    @Override
    public String getType() {
        return "jhipster";
    }

    public JHipsterGenerate(File yoRc, String name) {
        super(yoRc, name);
        this.yoRc = yoRc;
    }

    @Override
    public void run(File dir) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] { "yo", "jhipster" }, new String[] {}, dir);
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