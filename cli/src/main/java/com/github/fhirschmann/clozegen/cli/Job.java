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
import java.io.IOException;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.uima.UIMAException;
import org.apache.uima.resource.ResourceInitializationException;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Job {
    public void run(final CommandLine line, final String input, final String output)
            throws ResourceInitializationException, UIMAException, IOException {
        ClozeTestGenerator gen = ClozeTestGenerator.defaultGenerator();

        if (line.hasOption("classes")) {
            Map<String, Integer> classes = Utils.parseGapClasses(
                    line.getOptionValue("classes"));
            gen.activate(classes);
        }

        gen.run(input, output, "en");
    }
}
