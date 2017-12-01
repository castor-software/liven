package fr.inria.plugins.transformation.MavenDependencyVersionModification;

import fr.inria.core.Project;
import fr.inria.core.Result;
import fr.inria.core.TransformationStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.transformations.Envelope;

import java.io.File;
import java.util.Map;

public class DependencyVersionModification extends TransformationStep {
    File envelopeSrc;

    public DependencyVersionModification(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        envelopeSrc = new File(Project.getInstance().getPluginDir(this), "envelope.json");
    }

    @Override
    public Envelope buildEnvelope() {
        if(!this.conf.containsKey("pom")) System.err.println("No pom found.");
        return new fr.inria.plugins.transformation.MavenDependencyVersionModification.Envelope(
                new File(Project.getInstance().getTmpRoot(), this.conf.get("pom"))
        );
    }

    @Override
    public Envelope loadEnvelope() {
        if(!this.conf.containsKey("pom")) System.err.println("No pom found.");
        if(!hasEnvelope()) System.err.println("No envelope file found.");
        return new fr.inria.plugins.transformation.MavenDependencyVersionModification.Envelope(
                envelopeSrc,
                new File(Project.getInstance().getTmpRoot(), this.conf.get("pom"))
        );
    }

    @Override
    public boolean hasEnvelope() {
        return envelopeSrc.exists() && envelopeSrc.isFile();
    }

    @Override
    public void writeEnvelope(Envelope envelope) {
        envelope.writeToFile(envelopeSrc);
    }

    @Override
    public String getType() {
        return "maven-dependencies-version-modification";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    /*@Override
    public Result run(File dir) {
        return null;
    }*/
}
