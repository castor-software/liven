package fr.inria.core;

import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public static void writeFile(JSONObject obj, File f) {
        writeFile(obj.toString(), f);
    }

    public static void writeFile(String str, File f) {
        try {
            PrintWriter w = new PrintWriter(f);
            w.print(str);
            w.close();
        } catch (Exception ex) {
            System.err.println("Problem writing " + f.getPath());
            ex.printStackTrace();
        }
    }

    public static JSONObject readJSONFile(File f) throws JSONException {

        return new JSONObject(readFile(f));
    }

    public static String readFile(File in) {
        String result = null;
        try {
            InputStream input = new FileInputStream(in);
            if (input != null) {
                result = org.apache.commons.io.IOUtils.toString(input, java.nio.charset.Charset.forName("UTF-8"));
                input.close();
            } else {
                System.out.println("[Error] File not found: " + in.getPath());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null; // the template was not found
        }
        return result;
    }

    public static List<String> readFileAsList(File in) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
