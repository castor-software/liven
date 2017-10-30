package fr.inria.plugins;

import fr.inria.core.ConstructionStep;

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
    public void run() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec("yo jhipster");

            pr.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}