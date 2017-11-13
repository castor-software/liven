package fr.inria.cli;

public interface Command {

    String getName();

    /*String getUsage();

    String getDescription();

    boolean checkUsage(String args[]);

    void run(String args[]);*/

    void run();

}
