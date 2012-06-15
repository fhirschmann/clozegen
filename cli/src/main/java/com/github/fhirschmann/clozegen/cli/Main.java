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
import org.apache.uima.resource.ResourceInitializationException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Main {
    private HelpFormatter formatter;
    private Options options;

    static {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public Main() {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.INFO);
        formatter = new HelpFormatter();
        options = new Options();
    }

    public void printHelp() {
        formatter.printHelp("clozegen [options] INPUT OUTPUT", options);

    }

    public void run(final String[] args) throws ResourceInitializationException {
        CommandLineParser parser = new PosixParser();
        options.addOption("c", "classes", true, "word classes to generate gaps for");
        options.addOption("h", "help", false, "print help message and exit");
        options.addOption("l", "list", false, "print all available gap generators");

        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("help")) {
                printHelp();
            } else if (line.hasOption("list")) {
                for (DescriptionRegisterEntry entry : RegisterFactory.createDefaultRegister()) {
                    System.out.println(String.format("[%s] %s",
                            entry.getIdentifier(), entry.getName()));
                }
            } else {
                if (line.getArgs().length != 2) {
                    throw new ParseException("Exactly two arguments are required!");
                }
                Job run = new Job();
                run.run(line, line.getArgs()[0], line.getArgs()[1]);
            }

        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
            printHelp();
        }
    }

    public static void main(final String[] argv) throws Exception {
        Main main = new Main();
        main.run(argv);
    }
}
