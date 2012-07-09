/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
import com.github.fhirschmann.clozegen.lib.register.*;
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
                for (AnnotatorRegisterEntry entry : Registers.annotator()) {
                    System.out.println(String.format("[%s]%s %s",
                            entry.getIdentifier(), entry.getSupportedLanguages(),
                            entry.getName()));
                }
            } else if (line.hasOption("list-input-methods")) {
                for (ReaderRegisterEntry entry : Registers.reader()) {
                    System.out.println(String.format("[.%s] %s",
                            entry.getIdentifier(), entry.getName()));
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
