package fr.inria.core.YamlParsing;

import java.io.File;

public class ProjectInfo {

    public File tmp;
    public File variants;
    public File exploration;
    public File original;

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
