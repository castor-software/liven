package fr.inria.plugins;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import fr.inria.core.ConstructionStep;
import fr.inria.utils.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DockerBuild extends ConstructionStep {
    static Random rng = new Random();

    File dockerfile;
    String name = "liven-img";

    @Override
    public String getType() {
        return "docker";
    }

    public DockerBuild(File dockerfile, String name) {
        super(dockerfile, name);
        this.dockerfile = dockerfile;
        name += rng.nextInt(100000);
    }

    @Override
    public void run(File dir) {

        /*DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://my-docker-host.tld:2376")
                .withDockerTlsVerify(true)
                .withDockerCertPath("/home/user/.docker/certs")
                .withDockerConfig("/home/user/.docker")
                .withApiVersion("1.23")
                .withRegistryUrl("https://index.docker.io/v1/")
                .withRegistryUsername("docker")
                .withRegistryPassword("ilovedocker")
                .withRegistryEmail("dockeruser@github.com")
                .build();
        DockerClient docker = DockerClientBuilder.getInstance(config).build();*/
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(new String[] {"docker","build","-t", name, dockerfile.getAbsolutePath() }, new String[] {}, dir);
            StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream());
            StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream());
            errorGobbler.start();
            outputGobbler.start();
            pr.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
