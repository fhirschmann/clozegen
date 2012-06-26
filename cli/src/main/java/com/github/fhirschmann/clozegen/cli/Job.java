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
import com.github.fhirschmann.clozegen.lib.util.Resources2;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import org.apache.uima.UIMAException;
import org.apache.uima.resource.ResourceInitializationException;
import org.springframework.util.ResourceUtils;

/**
 * Represents a CLI Job.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Job {
    public void run(final Map<String, Integer> generators, final String input, final String output)
            throws ResourceInitializationException, UIMAException, IOException {
        ClozeTestGenerator gen = new ClozeTestGenerator();

        gen.activate(generators);

        File inputFile = new File(input);

        //URL inURL = Thread.currentThread().getContextClassLoader().getResource(input);
        gen.run(inputFile.toURI().toURL(), output, "en");
    }
}
