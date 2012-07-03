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

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.github.fhirschmann.clozegen.lib.ClozeTestGenerator;
import com.github.fhirschmann.clozegen.lib.plugins.Plugins;
import com.github.fhirschmann.clozegen.lib.register.DescriptionRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.ReaderRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.Registers;
import com.github.fhirschmann.clozegen.lib.register.WriterRegisterEntry;
import com.google.common.collect.Maps;

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
        Plugins.load();
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
     * <p>
     * You will need to pass a {@link ClozeTestGenerator} to this method. This
     * will allow you to, for example, add new input reader.
     * </p>
     *
     * @param gen the {@link ClozeTestGenerator} to use
     * @param args the arguments to the CLI
     */
    public void run(final ClozeTestGenerator gen, final String[] args) {
        CommandLineParser parser = new PosixParser();
        options.addOption("h", "help", false, "print help message and exit");
        options.addOption("g", "generators", true, "generators to activate");
        options.addOption("l", "language", true, "language of the input file");
        options.addOption(null, "list-generators", false,
                "list all available gap generators");
        options.addOption(null, "list-input-methods", false,
                "list all available input methods");
        options.addOption(null, "list-output-methods", false,
                "list all available output methods");
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
                System.exit(0);
            }

            if (line.hasOption("list-generators")) {
                for (DescriptionRegisterEntry entry : Registers.annotator()) {
                    System.out.println(String.format("[%s] %s",
                            entry.getIdentifier(), entry.getName()));
                }
            } else if (line.hasOption("list-input-methods")) {
                for (Entry<String, ReaderRegisterEntry> entry : Registers.
                        reader().entrySet()) {
                    System.out.println(String.format("[.%s] %s",
                            entry.getKey(), entry.getValue().getName()));
                }
            } else if (line.hasOption("list-output-methods")) {
                for (WriterRegisterEntry entry : Registers.writer()) {
                    System.out.println(String.format("[.%s] %s",
                            entry.getIdentifier(), entry.getName()));
                }
            } else {
                checkArgument(line.getArgs().length == 2,
                        "Exactly two arguments are required!");

                Map<String, Integer> generators = Maps.newHashMap();

                if (line.hasOption("generators")) {
                    generators.putAll(Utils.parseGapClasses(
                            line.getOptionValue("generators")));
                }

                gen.activate(generators);

                File inputFile = new File(line.getArgs()[0]);
                File outputFile = new File(line.getArgs()[1]);

                gen.run(inputFile.toURI().toURL(), outputFile.toURI().toURL(),
                        line.hasOption("language")
                        ? line.getOptionValue("language") : "en");
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
        ClozeTestGenerator gen = new ClozeTestGenerator();
        main.run(gen, argv);
    }
}
