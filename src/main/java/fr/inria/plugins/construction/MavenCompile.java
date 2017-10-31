package fr.inria.plugins.construction;

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
    public void run(File dir) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( pom );
        request.setGoals( Arrays.asList( "clean", "compile" ) );
        request.getOutputHandler(new InvocationOutputHandler() {
            @Override
            public void consumeLine(String s) {
                System.out.println(s);
            }
        });
        request.getErrorHandler(new InvocationOutputHandler() {
            @Override
            public void consumeLine(String s) {
                System.out.println(s);
            }
        });

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(getMavenHome());
        invoker.setWorkingDirectory(dir);
        try {
            invoker.execute( request );
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }

    protected File getMavenHome() {
        //freebsd
        File mvnHome = new File("/usr/local/share/java/maven3");
        if (!mvnHome.exists()) {
            //ubuntu 2
            mvnHome = new File("/usr/share/maven");
        }
        if (!mvnHome.exists()) {
            //ubuntu
            mvnHome = new File("/usr/share/maven-3.3.3");
        }
        if (!mvnHome.exists()) {
            //debian
            mvnHome = new File("/opt/maven");
        }
        if (!mvnHome.exists()) {
            //osx
            mvnHome = new File("/usr/local/Cellar/maven/3.3.9/libexec/");
        }
        return mvnHome;
    }
}
