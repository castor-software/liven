package fr.inria.core;

import java.io.File;

public class ProjectInfo {

    File tmp;
    File variants;
    File exploration;
    File original;

    public void setTmp(File tmp) {
        this.tmp = tmp;
    }

    public void setVariants(File variants) {
        this.variants = variants;
    }

    public void setExploration(File exploration) {
        this.exploration = exploration;
    }

    public void setOriginal(File original) {
        this.original = original;
    }
}
