package fr.inria.plugins.transformation;

import com.github.dockerjava.core.dockerfile.Dockerfile;
import fr.inria.core.FileUtils;
import fr.inria.core.Result;
import fr.inria.core.TransformationStep;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.transformations.Envelope;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwapDockerBaseImageTransformation extends TransformationStep {
    public SwapDockerBaseImageTransformation(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    @Override
    public Envelope buildEnvelope() {
        return null;
    }

    @Override
    public Envelope loadEnvelope() {
        return null;
    }

    @Override
    public boolean hasEnvelope() {
        return false;
    }

    @Override
    public void writeEnvelope(Envelope envelope) {

    }

    /*@Override
    public void generateExplorationRoadMap(File roadMap) {
        throw new NotImplementedException();
    }

    @Override
    public void explore(File roadMap, File explorationResults) {

    }

    @Override
    public void transform(File explorationResults) {
        Random r = new Random();
        List<String> baseImages = FileUtils.readFileAsList(explorationResults);
        File dockerfile = new File(conf.get("Dockerfile"));

        List<String> dockerfileStmts = FileUtils.readFileAsList(dockerfile);

        Pattern p = Pattern.compile("\\s*FROM\\s+\\w+(-\\w+|)+(\\/\\w+(-\\w+|)+|)(:\\w+(-\\w+|)+|)");
        for(int i = 0; i < dockerfileStmts.size(); i++) {
            String stmt = dockerfileStmts.get(i);
            Matcher m = p.matcher(stmt);
            if(m.matches()) {
                stmt = "FROM " + baseImages.get(r.nextInt(baseImages.size()));
                dockerfileStmts.set(i, stmt);
            }
        }
    }*/

    @Override
    public String getType() {
        return "docker-base-image-swap";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return false;
    }

    @Override
    public Result run(File dir) {
        return null;
    }
}
