package fr.inria.core.transformations;

import fr.inria.core.Cycle;
import fr.inria.core.Project;
import fr.inria.core.Result;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class Envelope {
    public Set<MutationPoint> mutationPoints = new HashSet<>();

    public void explore(Cycle cycle) {
        for(MutationPoint mp: mutationPoints) {
            Set<Mutation> toRemove = new HashSet<>();
            for(Mutation m: mp.alternatives) {
                m.apply();
                Result r = cycle.run(Project.getInstance().getTmpRoot());
                m.revert();
                if(!r.isSuccess()) toRemove.add(m);
            }
            for(Mutation m : toRemove) {
                mp.removeAlternative(m);
            }
        }
    }

    public Set<Envelope> split(int nb) {
        return null;
    }

    public abstract void writeToFile(File f);
}
