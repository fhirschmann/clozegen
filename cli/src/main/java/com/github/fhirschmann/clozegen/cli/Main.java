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

import com.github.fhirschmann.clozegen.lib.ClozeTestGenerator;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.cli.*;
import org.apache.uima.resource.ResourceInitializationException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Main {
    public static void main(final String[] argv) throws ResourceInitializationException {
        CommandLineParser parser = new PosixParser();
        Options options = new Options();
        options.addOption("c", "classes", true, "word classes to generate gaps for");
        options.addOption("h", "help", false, "print help message and exit");

        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine line = parser.parse(options, argv);

            if (line.hasOption("help")) {
                formatter.printHelp("clozegen [options] INPUT OUTPUT", options);
                System.exit(0);
            }

            if (line.getArgs().length != 2) {
                throw new ParseException("Exactly two arguments are required!");
            }

            List<String> classes = Lists.newArrayList(line.getOptionValue(
                    "classes", "prepositions").split(","));
            String input = line.getArgs()[0];
            String output = line.getArgs()[1];

            ClozeTestGenerator clozegen = new ClozeTestGenerator();

        } catch (ParseException exp) {
            System.err.println(exp.getMessage());
        }
    }
}
