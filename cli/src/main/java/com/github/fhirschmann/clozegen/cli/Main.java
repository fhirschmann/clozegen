/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.cli;

import com.github.fhirschmann.clozegen.lib.register.DescriptionRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.RegisterFactory;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Command line entry class. All parsing should be done in this class.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Main {
    private HelpFormatter formatter;
    private Options options;

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    /**
     * Creates a new instance.
     */
    public Main() {
        formatter = new HelpFormatter();
        options = new Options();
    }

    /**
     * Prints the help message.
     */
    public void printHelp() {
        formatter.printHelp("clozegen [options] INPUT OUTPUT", options);
    }

    /**
     * Runs the command line interface.
     *
     * @param args the arguments to the CLI
     */
    public void run(final String[] args) {
        CommandLineParser parser = new PosixParser();
        options.addOption("g", "generators", true, "generators to activate");
        options.addOption("h", "help", false, "print help message and exit");
        options.addOption("l", "list", false, "print all available gap generators");
        options.addOption("d", "debug", false, "enable debug mode");
        boolean debug = true;


        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("debug")) {
                Logger.getRootLogger().setLevel(Level.DEBUG);
            } else {
                Logger.getRootLogger().setLevel(Level.INFO);
                debug = false;
            }

            if (line.hasOption("help")) {
                printHelp();
            } else if (line.hasOption("list")) {
                for (DescriptionRegisterEntry entry : RegisterFactory.
                        createDefaultAnnotatorRegister()) {
                    System.out.println(String.format("[%s] %s",
                            entry.getIdentifier(), entry.getName()));
                }
            } else {
                checkArgument(line.getArgs().length == 2,
                        "Exactly two arguments are required!");
                Job job = new Job();

                Map<String, Integer> generators = Maps.newHashMap();

                if (line.hasOption("generators")) {
                    generators.putAll(Utils.parseGapClasses(
                            line.getOptionValue("generators")));
                }

                job.run(generators, line.getArgs()[0], line.getArgs()[1]);
            }

        } catch (Exception exp) {
            if (debug) {
                exp.printStackTrace();
            } else {
                System.err.println("ERROR: " + exp.getMessage());
            }
            printHelp();
        }
    }

    /**
     * Passes the arguments to {@link Main#main(java.lang.String[])}.
     *
     * @param argv the arguments
     */
    public static void main(final String[] argv) {
        Main main = new Main();
        main.run(argv);
    }
}
