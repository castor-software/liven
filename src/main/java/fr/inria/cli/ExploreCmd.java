package fr.inria.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Explores the envelop of acceptability of a particular cycle")
public class ExploreCmd implements Command {

    @Parameter(description = "<cycle to explore>", required = true)
    String cycle;

    @Parameter(names = {"--pre-explore", "-p"}, description = "Stop at roadmap generation")
    private Boolean amend = false;

    @Override
    public String getName() {
        return "explore";
    }

    public void run() {

    }
}
