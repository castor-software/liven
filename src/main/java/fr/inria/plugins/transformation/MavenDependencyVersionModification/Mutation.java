package fr.inria.plugins.transformation.MavenDependencyVersionModification;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mutation extends fr.inria.core.transformations.Mutation {
    String original;
    Dependency dependency;
    String newVersion;
    Model model;

    public Mutation(Dependency dependency, String newVersion, Model model) {
        this.dependency = dependency;
        this.newVersion = newVersion;
        this.original = dependency.getVersion();
        this.model = model;
    }

    @Override
    public void apply() {
        dependency.setVersion(newVersion);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            writer.write(new FileOutputStream(model.getPomFile()), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        dependency.setVersion(original);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            writer.write(new FileOutputStream(model.getPomFile()), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
