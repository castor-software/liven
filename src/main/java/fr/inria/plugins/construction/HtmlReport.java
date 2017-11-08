package fr.inria.plugins.construction;

import fr.inria.core.ConstructionStep;
import fr.inria.core.Result;
import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;

import java.io.File;
import java.util.Map;

public class HtmlReport extends ConstructionStep {
    public HtmlReport(Map<String, String> conf, String name) throws IncorrectYAMLInformationException {
        super(conf, name);
    }

    @Override
    public String getType() {
        return "html-report";
    }

    @Override
    public boolean isObliviousToPreviousFailure() {
        return true;
    }

    @Override
    public Result run(File dir) {
        Result result = new Result(-1, "Plugin " + getType() + " is not implemented");
        return result;
    }
}
