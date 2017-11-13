package fr.inria.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Display this message")
public class HelpCmd implements Command {

    @Parameter(description = "<command>")
    String command = null;

    JCommander jc;
    public HelpCmd() {}

    public void setJc(JCommander jc) {
        this.jc = jc;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void run() {
        if(command == null)
            jc.usage();
        else
            jc.usage(command);
    }
}
