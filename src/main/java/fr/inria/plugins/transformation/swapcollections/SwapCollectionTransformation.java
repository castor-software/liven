package fr.inria.plugins.transformation.swapcollections;

import fr.inria.core.FileUtils;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.Result;
import fr.inria.core.TransformationStep;
import org.json.JSONException;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.util.Map;

public class SwapCollectionTransformation extends TransformationStep {
    Map<String, String> conf;
    public SwapCollectionTransformation(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
        this.conf = conf;
    }

    @Override
    public void generateExplorationRoadMap(File roadMap, Map<String, String> models) {
        ExplorationRoadMap exp = new ExplorationRoadMap();

        File pom = new File(conf.get("pom"));

        MavenLauncher launcher = new MavenLauncher(pom.getParentFile().getAbsolutePath(), MavenLauncher.SOURCE_TYPE.APP_SOURCE);
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.buildModel();
        CtModel model = launcher.getModel();

        for(CtType<?> s : model.getAllTypes()) {
            if(s instanceof CtClass)
                exp.processClass((CtClass) s);
        }

        try {
            FileUtils.writeFile(exp.getJSONRoadMap(), roadMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void explore(File roadMap, File explorationResults, Map<String, String> models) {

    }

    @Override
    public void transform(File explorationResults, Map<String, String> models) {

    }

    @Override
    public String getType() {
        return "swap-collections";
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
