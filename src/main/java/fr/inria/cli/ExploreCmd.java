package fr.inria.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import fr.inria.core.Cycle;
import fr.inria.core.Project;
import fr.inria.core.TransformationStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.transformations.Envelope;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

@Parameters(commandDescription = "Explores the envelop of acceptability of a particular cycle")
public class ExploreCmd implements Command {

    @Parameter(description = "<cycle to explore>", required = true)
    String cycle;

    @Parameter(names = {"-t"}, description = "Transformation step to explore. Default: all transformation of the cycle")
    String transformation = null;

    @Override
    public String getName() {
        return "explore";
    }

    public void run() {

        Project lifeCycle = new Project();
        File cycleFile = new File("cycles.yml");
        try {
            lifeCycle.parseYaml(cycleFile);
            lifeCycle.createTmp();
            Cycle cy = lifeCycle.getCycle(cycle);

            List<TransformationStep> transformations;
            if(transformation != null) {
                transformations = new LinkedList<>();
                if(!lifeCycle.containsCycle(transformation))
                    System.err.println("cycles.yml does not contains " + transformation);
                else
                    transformations.add((TransformationStep) lifeCycle.getStep(transformation));
            } else {
                transformations = lifeCycle.getTransformations(cy);
            }

            for(TransformationStep t : transformations) {
                Envelope envelope = t.getEnvelope();
                envelope.explore(cy);
                t.writeEnvelope(envelope);
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
