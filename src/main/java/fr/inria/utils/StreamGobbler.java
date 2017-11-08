package fr.inria.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
    InputStream is;

    boolean isSilent = false;

    public void setIsSilent(boolean isSilent) { this.isSilent = isSilent; }

    String output = "";

    public String getOutput() { return output; }

    public StreamGobbler(InputStream is) {
        this.is = is;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ( (line = br.readLine()) != null) {
                output += line;
                if(!isSilent) {
                    System.out.println(line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
