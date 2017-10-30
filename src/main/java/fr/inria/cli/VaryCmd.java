package fr.inria.cli;

public class VaryCmd implements Command {

    public String getUsage() {
        return "lvn vary <cycle> [#variants]";
    }

    public String getDescription() {
        return "Generates #variants (default 1) for the given cycle";
    }

    public boolean checkUsage(String[] args) {
        if(args.length < 1 || args.length > 2)
            return false;
        return true;
    }

    public void run(String[] args) {

    }
}
