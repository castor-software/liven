package fr.inria.core;

import java.io.File;

public interface Step {
    String getName();
    String getType();

    void run(File dir);
}
