package fr.inria.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import fr.inria.core.LifeCycle;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Parameters(commandDescription = "List available cycles or run the specified cycle on original")
public class CyclesCmd implements Command {

    @Parameter(description = "<cycle to run>")
    private String cycle = null;

    @Override
    public String getName() {
        return "cycles";
    }

    public void run() {

        LifeCycle lifeCycle = new LifeCycle();
        File cycleFile = new File("cycles.yml");
        try {
            lifeCycle.parseYaml(cycleFile);

            if(cycle == null) {
                lifeCycle.listCycles();
            } else {
                lifeCycle.runCycle(cycle);
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
