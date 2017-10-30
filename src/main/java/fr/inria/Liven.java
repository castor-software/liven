package fr.inria;

import fr.inria.cli.Command;
import fr.inria.cli.CyclesCmd;
import fr.inria.cli.ExploreCmd;
import fr.inria.cli.VaryCmd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Liven {

    public static Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("cycles", new CyclesCmd());
        commands.put("explore", new ExploreCmd());
        commands.put("vary", new VaryCmd());
    }

    public static void main( String[] args )
    {
        System.out.println( "------------------------------------------------------------------------" );

        if(args.length < 1 || args[0].equalsIgnoreCase("help")) {
            printUsage();
        } else {

            String cmdString = args[0];

            if (!commands.containsKey(cmdString)) printUsage();

            Command cmd = commands.get(cmdString);

            String parameters[] = Arrays.copyOfRange(args, 1, args.length);

            if (!cmd.checkUsage(parameters)) {
                System.out.println(cmd.getUsage());
            } else {
                cmd.run(parameters);
            }

        }
        System.out.println( "------------------------------------------------------------------------" );
    }

    public static void printUsage() {

        System.out.println("Usage: lvn command [args]:");
        for(Map.Entry<String,Command> e: commands.entrySet()) {
            System.out.println("\t" + e.getKey() + "\t : " + e.getValue().getDescription());
        }
        System.out.println("\thelp\t : Display this message");
    }
}
