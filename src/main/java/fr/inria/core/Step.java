package fr.inria.core;

public interface Step {
    String getName();
    String getType();

    void run();
}
