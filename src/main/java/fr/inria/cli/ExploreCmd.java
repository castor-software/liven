package fr.inria.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import fr.inria.core.*;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.transformations.Envelope;
import fr.inria.core.transformations.Mutation;

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
            List<Step> steps = cy.getChildren();

            int nbRun = 0;
            for(Step s : steps) {
                if(s instanceof TransformationStep) {
                    TransformationStep t = (TransformationStep) s;
                    Envelope envelope = t.getEnvelope();
                    t.envelope = envelope;
                    t.mutations = envelope.getMutations(cy);
                    nbRun += t.mutations.size();
                }
            }

            for(int i = 0; i < nbRun; i++) {


                System.out.println( "------------------------------------------------------------------------" );
                System.out.println( " Exploration n° " + i );
                System.out.println( "------------------------------------------------------------------------" );
                Result r;
                Mutation m = null;
                TransformationStep t = null;
                for(Step step : steps) {
                    if(step instanceof TransformationStep) {
                        t = (TransformationStep) step;
                        if(m == null && !t.mutations.isEmpty()) {
                            m = t.mutations.get(0);
                            m.apply();
                            t.mutations.remove(0);
                        }
                    } else {
                        r = step.run(Project.getInstance().getTmpRoot());
                        if (!r.isSuccess()) {
                            if(t != null) m.site.removeAlternative(m);
                            else System.err.println("[Error] A step premutation has failed!");
                            break;
                        }
                    }
                }
                if(t.mutations.isEmpty()) t.writeEnvelope(t.envelope);
                m.revert();
            }


            /*List<TransformationStep> transformations;
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
            }*/

        } catch (FileNotFoundException e) {
            System.err.println("[Error] No cycles.yml found");
            //e.printStackTrace();
        } catch (IncorrectYAMLInformationException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
