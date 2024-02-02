package com.mycompany.util;

/**
 *
 * @author vikai
 */

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

/**
 *
 * @author vikai
 */
public class Parser {
    @Option(name = "-o")
    private String GivenPath;
    @Option(name = "-p")
    private String Prefix;
    @Option(name = "-a")
    private boolean Adding = false;
    @Option(name = "-s")
    private boolean ShortStats = false;
    @Option(name = "-f")
    private boolean FullStats = false;

    @Argument
    private String[] arguments;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new Parser().run(args);
    }

    private void run(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            // пока оставим, что можно без статистики и что можно обе статистики
            if (arguments == null) {
                System.err.println("No arguments entered");
                parser.printUsage(System.err);
                throw new IllegalArgumentException("");
            }
            else {
                FlagProcessing.FileRecording(arguments, GivenPath, Prefix, Adding, ShortStats, FullStats);
            }

        } catch (CmdLineException | IOException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
    }
}