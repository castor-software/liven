package fr.inria.plugins.transformation.MavenDependencyVersionModification;

import fr.inria.core.FileUtils;
import fr.inria.core.Project;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mutation extends fr.inria.core.transformations.Mutation {
    String original;
    Dependency dependency;
    String newVersion;
    Model model;
    File pom;

    public Mutation(Dependency dependency, String newVersion, Model model, File pom) {
        this.dependency = dependency;
        this.newVersion = newVersion;
        this.original = dependency.getVersion();
        this.model = model;
        this.pom = pom;
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        try {
            o.put("group", dependency.getGroupId());
            o.put("artifact", dependency.getArtifactId());
            o.put("version", newVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public void apply() {
        System.out.println("apply: "
                + dependency.getGroupId() + "." + dependency.getArtifactId() + ": "
                + original + " -> " + newVersion);
        dependency.setVersion(newVersion);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        FileUtils.writeFile(toJSON(), new File(Project.getInstance().getTmpRoot(), "mutant.json"));
        try {
            writer.write(new FileOutputStream(pom), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        dependency.setVersion(original);
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            writer.write(new FileOutputStream(pom), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
