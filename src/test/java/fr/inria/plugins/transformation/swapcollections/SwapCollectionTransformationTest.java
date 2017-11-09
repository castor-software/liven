package fr.inria.plugins.transformation.swapcollections;

import fr.inria.core.FileUtils;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SwapCollectionTransformationTest extends TestCase {
    public void testGenerateExplorationRoadMap() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        Map<String, String> map = new HashMap<>();
        map.put("pom", classLoader.getResource("Models/javaSources/dummya/pom.xml").getPath());

        SwapCollectionTransformation t = new SwapCollectionTransformation(map, "test");
        File res = new File("roadMap.json");
        res.createNewFile();
        t.generateExplorationRoadMap(res, map);

        JSONObject o = FileUtils.readJSONFile(res);
        JSONArray a = o.getJSONArray("TransformationPoints");

        assertTrue(a.length() == 20);

        res.delete();
    }

}