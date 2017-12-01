package fr.inria.core.transformations;

public abstract class Mutation {
    public MutationPoint site;

    public abstract void apply();
    public abstract void revert();
}
