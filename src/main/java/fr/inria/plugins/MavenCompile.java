package fr.inria.plugins;

import fr.inria.core.ConstructionStep;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;

public class MavenCompile extends ConstructionStep {

    File pom;

    @Override
    public String getType() {
        return "maven-compile";
    }

    public MavenCompile(File pom, String name) {
        super(pom, name);
        this.pom = pom;
    }

    @Override
    public void run() {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( pom );
        request.setGoals( Arrays.asList( "clean", "compile" ) );

        Invoker invoker = new DefaultInvoker();
        try {
            invoker.execute( request );
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}
