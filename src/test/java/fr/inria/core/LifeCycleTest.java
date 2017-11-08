package fr.inria.core;

import fr.inria.core.YamlParsing.IncorrectYAMLInformationException;
import junit.framework.TestCase;

import java.io.File;

public class LifeCycleTest extends TestCase {
    public void testParseYaml() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File correct = new File(classLoader.getResource("Correct").getFile());
        for(File yml: correct.listFiles()) {
            LifeCycle l = new LifeCycle();
            l.parseYaml(yml);
            l.listCycles();
        }
        File incorrect = new File(classLoader.getResource("Incorrect").getFile());
        for(File yml: incorrect.listFiles()) {
            LifeCycle l = new LifeCycle();
            try {
                l.parseYaml(yml);
                fail("No exceptions");
            } catch (IncorrectYAMLInformationException e) {
                assertTrue(!e.getMessage().equals(""));
            }
        }
    }

}