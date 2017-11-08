package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

public class MavenCompile extends ConstructionStep {

    File pom;

    @Override
    public String getType() {
        return "maven-compile";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    public MavenCompile(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        if(conf.containsKey("pom")) {
            this.pom = new File(conf.get("pom"));
        } else {
            throw new IncorrectYAMLInformationException("Missing pom in models");
        }
    }

    @Override
    public Result run(File dir) {
        Result result;
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
            InvocationResult ir = invoker.execute( request );
            result = new Result(ir.getExitCode(), ir.getExecutionException().getMessage());
        } catch (MavenInvocationException e) {
            result = new Result(-1, e.getMessage());
        }
        return result;
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
