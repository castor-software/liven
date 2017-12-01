package fr.inria.plugins.transformation.MavenDependencyVersionModification;

import fr.inria.core.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Envelope extends fr.inria.core.transformations.Envelope {

    public Envelope(File pom) {

        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileInputStream(pom));

            for(Dependency d :model.getDependencies()) {
                MutationPoint mp = new MutationPoint(d);
                this.mutationPoints.add(mp);
                List<String> alternatives = getDependencyVersionList(d);
                for(String version : alternatives) {
                    mp.addAlternative(new Mutation(d, version, model));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public Envelope(File json, File pom) {
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileInputStream(pom));
            JSONObject envelope = FileUtils.readJSONFile(json);
            JSONArray mps = envelope.getJSONArray("mutationPoints");
            Map<String, Dependency> dependencies = new HashMap<>();
            for(Dependency d: model.getDependencies()) {
                dependencies.put(d.getGroupId() + "." + d.getArtifactId(), d);
            }

            for(int i = 0; i < mps.length(); i++) {
                JSONObject mp = mps.getJSONObject(i);
                Dependency d = dependencies.get(mp.getString("group") + "." + mp.getString("artifact"));
                MutationPoint mutationPoint = new MutationPoint(d);
                JSONArray als = mp.getJSONArray("alternatives");
                for(int j = 0; j < als.length(); j++) {
                    mutationPoint.addAlternative(new Mutation(d,als.getString(j),model));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public List<String> getDependencyVersionList(Dependency d) {
        List<String> res = new ArrayList<>();


        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String g = d.getGroupId();
            String a = d.getArtifactId();
            URI uri = new URI("http://search.maven.org/solrsearch/select?q=g:%22" + g + "%22+AND+a:%22" + a +"%22&rows=100&core=gav&wt=json");
            HttpGet request = new HttpGet(uri);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            JSONObject j = new JSONObject(json);
            JSONArray list = j.getJSONObject("response")
                    .getJSONArray("docs");
            if(list.length() == 0) return res;
            for(int i = 0; i < list.length(); i++) {
                res.add(list.getJSONObject(i).getString("v"));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  res;
    }

    @Override
    public void writeToFile(File f) {
        try {
            JSONObject e = new JSONObject();

            JSONArray mps = new JSONArray();
            for(fr.inria.core.transformations.MutationPoint mutationPoint1: this.mutationPoints) {
                MutationPoint mutationPoint = (MutationPoint) mutationPoint1;
                JSONObject mp = new JSONObject();
                mp.put("group", mutationPoint.d.getGroupId());
                mp.put("artifact", mutationPoint.d.getArtifactId());
                JSONArray ms = new JSONArray();
                for(fr.inria.core.transformations.Mutation mutation1: mutationPoint.alternatives) {
                    Mutation mutation = (Mutation) mutation1;
                    ms.put(mutation.newVersion);
                }
                mp.put("alternatives", ms);
                mps.put(mp);
            }
            e.put("mutationPoints", mps);

            FileUtils.writeFile(e,f);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
}
