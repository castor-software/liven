package fr.inria.plugins.transformation.MavenDependencyVersionModification;

import org.apache.maven.model.Dependency;

public class MutationPoint extends fr.inria.core.transformations.MutationPoint {
    public Dependency d;

    public MutationPoint(Dependency d) {
        this.d = d;
    }
}
