package fr.inria.core.transformations;

import java.util.Set;

public abstract class Mutant {
    public Set<Mutation> mutations;

    public void build() {
        for (Mutation m : mutations) {
            m.apply();
        }
    }
}
