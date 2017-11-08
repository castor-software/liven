package fr.inria.core.YamlParsing;

import java.io.File;
import java.util.Map;

public class StepInfo {
    public String name;
    public String type;
    public Map<String, String> conf;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConf(Map<String,String> conf) {
        this.conf = conf;
    }

}
