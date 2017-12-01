package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ConstructionStep extends AbstractStep {

    public ConstructionStep(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    public List<Step> getChildren() {
        List<Step> res = new ArrayList<>();
        res.add(this);
        return res;
    }

}
