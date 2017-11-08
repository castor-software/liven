package fr.inria.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Cycle implements Step {

    List<Step> steps = new ArrayList<>();
    String name;

    public Cycle(String name) {
        this.name = name;
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "cycle";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    @Override
    public Result run(File dir) {
        Result result = new Result(0, "Nothing has been done yet");

        for (Step step : steps) {

            if(result.status == 0 || step.isObliviousToPreviousFailure()) {

                System.out.println( "------------------------------------------------------------------------" );
                System.out.println( "Running " + step.getName() );
                System.out.println( "------------------------------------------------------------------------" );

                Result cur = step.run(dir);
                if(cur.status != 0) {
                    result.status = cur.status;
                    System.out.println( "------------------------------------------------------------------------" );
                    System.err.println( "Error: " + cur.message );
                    System.out.println( "------------------------------------------------------------------------" );
                }

                result.message += cur.message;
            } else if (!step.isObliviousToPreviousFailure()) {
                System.out.println( "------------------------------------------------------------------------" );
                System.err.println( "Skipping: " + step.getName() );
                System.out.println( "------------------------------------------------------------------------" );
            }
        }

        return result;
    }
}
