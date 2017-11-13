package fr.inria.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Generates #variants (default 1) for the given cycle")
public class VaryCmd implements Command {
    @Parameter(description = "<cycle to be used>", required = true)
    String cycle;

    @Parameter(names = "-n", description = "nb of variants to generate")
    int nbVariant = 1;

    @Override
    public String getName() {
        return "vary";
    }

    public void run() {

    }
}
