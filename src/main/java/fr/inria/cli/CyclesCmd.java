package fr.inria.cli;

import fr.inria.core.LifeCycle;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.io.File;
import java.io.FileNotFoundException;

public class CyclesCmd implements Command {

    @Override
    public String getUsage() {
        return "lvn cycles [<cycle>]";
    }

    @Override
    public String getDescription() {

        return "List available cycles or run the specified cycle on original";
    }

    @Override
    public boolean checkUsage(String[] args) {
        if(args.length > 1)
            return false;
        else
            return true;
    }

    @Override
    public void run(String args[]) {
        if(!checkUsage(args)) return; // Should not happen

        LifeCycle lifeCycle = new LifeCycle();
        File cycleFile = new File("cycles.yml");
        try {
            lifeCycle.parseYaml(cycleFile);

            if(args.length == 0) {
                lifeCycle.listCycles();
            } else {
                lifeCycle.runCycle(args[0]);
            }

        } catch (FileNotFoundException e) {
            System.err.println("[Error] No cycles.yml found");
            //e.printStackTrace();
        } catch (IncorrectYAMLInformationException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
