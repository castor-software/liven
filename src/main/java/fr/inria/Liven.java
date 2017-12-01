package fr.inria;

import com.beust.jcommander.JCommander;
import fr.inria.cli.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spoon.testing.utils.ModelUtils.build;

/**
 * Hello world!
 *
 */
public class Liven {

    public static void main( String[] args )
    {
        System.out.println( "------------------------------------------------------------------------" );
        Liven cm = new Liven();
        Command cycles = new CyclesCmd();
        Command explore = new ExploreCmd();
        Command create = new CreateEnvelopeCmd();
        Command vary = new VaryCmd();
        Command help = new HelpCmd();

        Map<String, Command> commands;
        commands = new HashMap<>();
        commands.put(cycles.getName(), cycles);
        commands.put(explore.getName(),explore);
        commands.put(create.getName(),create);
        commands.put(vary.getName(),vary);
        commands.put(help.getName(),help);

        JCommander jc = JCommander.newBuilder()
                .addObject(cm)
                .addCommand("cycles", cycles)
                .addCommand("explore", explore)
                .addCommand("create", create)
                .addCommand("vary", vary)
                .addCommand("help", help)
                .build();

        ((HelpCmd) help).setJc(jc);
        jc.parse(args);

        Command cmd = commands.get(jc.getParsedCommand());

        if(cmd == null) {
            jc.usage();
        } else {
            cmd.run();
        }
        System.out.println( "------------------------------------------------------------------------" );
    }
}
