package fr.inria.core;

import junit.framework.TestCase;

import java.io.File;

public class LifeCycleTest extends TestCase {
    public void testParseYaml() throws Exception {
        LifeCycle l = new LifeCycle();
        ClassLoader classLoader = getClass().getClassLoader();
        File cycle = new File(classLoader.getResource("cycle.yml").getFile());
        l.parseYaml(cycle);
    }

}