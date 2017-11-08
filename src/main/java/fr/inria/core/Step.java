package fr.inria.core;

import java.io.File;

public interface Step {
    String getName();
    String getType();
    boolean isObliviousToPreviousFailure();

    Result run(File dir);
}
