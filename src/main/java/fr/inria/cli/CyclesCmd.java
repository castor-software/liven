package fr.inria.cli;

import fr.inria.core.LifeCycle;

import java.io.File;
import java.io.FileNotFoundException;

public class CyclesCmd implements Command {

    @Override
    public String getUsage() {
        return "lvn cycles";
    }

    @Override
    public String getDescription() {

        return "List available cycles";
    }

    @Override
    public boolean checkUsage(String[] args) {
        if(args.length > 0)
            return false;
        else
            return true;
    }

    @Override
    public void run(String args[]) {
        if(!checkUsage(args)) return; // Should not happen

        LifeCycle lifeCycle = new LifeCycle();
        File cycleFile = new File("cycle.yml");
        try {
            lifeCycle.parseYaml(cycleFile);

            lifeCycle.listCycles();

        } catch (FileNotFoundException e) {
            System.err.println("[Error] No cycle.yml found");
            //e.printStackTrace();
        }
    }
}
