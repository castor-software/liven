package fr.inria.core.transformations;

import java.util.HashSet;
import java.util.Set;

public abstract class MutationPoint {
    public Set<Mutation> alternatives = new HashSet<>();

    public Set<Mutant> getMonoMutants() {
        Set<Mutant> mutants = new HashSet<>();
        for(Mutation mutation : alternatives) {
            mutants.add(new Mutant() {
                @Override
                public void build() {
                    super.build();
                }
            });
        }
        return mutants;
    }

    public void addAlternative(Mutation m) {
        m.site = this;
        alternatives.add(m);
    }

    public void removeAlternative(Mutation m) {
        alternatives.remove(m);
    }
}
