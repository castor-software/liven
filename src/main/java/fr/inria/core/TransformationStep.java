package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import fr.inria.core.transformations.Envelope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TransformationStep extends AbstractStep {
    public TransformationStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    public abstract Envelope buildEnvelope();

    public abstract Envelope loadEnvelope();

    public abstract boolean hasEnvelope();

    public Envelope getEnvelope() {
        if(hasEnvelope()) return loadEnvelope();
        else return buildEnvelope();
    }

    public abstract void writeEnvelope(Envelope envelope);

    /*public abstract void generateExplorationRoadMap(File roadMap);

    public abstract void explore(File roadMap, File explorationResults);

    public abstract void transform(File explorationResults);*/

    public List<Step> getChildren() {
        List<Step> res = new ArrayList<>();
        res.add(this);
        return res;
    }

    @Override
    public Result run(File dir) {
        //TODO: FIXME "Transformations should be applied at their proper place in the cycle"
        return new Result(0,"OK");
    }
}
