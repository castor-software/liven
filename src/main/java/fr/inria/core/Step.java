package fr.inria.core;

import java.io.File;
import java.util.List;

public interface Step {
    String getName();
    String getType();
    boolean isObliviousToPreviousFailure();
    List<Step> getChildren();

    Result run(File dir);
}
