package fr.inria.cli;

public class ExploreCmd implements Command {

    public String getUsage() {
        return "lvn explore <cycle>";
    }

    public String getDescription() {
        return "Explores the envelop of acceptability of a particular cycle";
    }

    public boolean checkUsage(String[] args) {
        if(args.length != 1)
            return false;
        return true;
    }

    public void run(String[] args) {

    }
}
