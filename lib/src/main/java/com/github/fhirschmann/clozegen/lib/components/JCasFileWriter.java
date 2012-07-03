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
package com.github.fhirschmann.clozegen.lib.components;

import com.github.fhirschmann.clozegen.lib.components.api.JCasFormatter;
import com.github.fhirschmann.clozegen.lib.components.api.OutputFileWriter;
import com.google.common.io.Closeables;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.descriptor.ExternalResource;

/**
 * Writes a {@link JCas} to an output file using the a given
 * {@link JCasFileWriter#FORMATTER_KEY}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class JCasFileWriter extends OutputFileWriter {
    /**
     * The writer we will write to.
     */
    private BufferedWriter outputBuffer;

    /**
     * <em>[mandatory]</em>
     *
     * The argument to this keyword should be a {@link GeneratorAdapter} which
     * knows how to create a {@link GapGenerator}.
     */
    public static final String FORMATTER_KEY = "Formatter";
    @ExternalResource(key = FORMATTER_KEY)
    private JCasFormatter formatter;

    @Override
    public void initialize(final UimaContext context) throws
            ResourceInitializationException {
        super.initialize(context);

        try {
            outputBuffer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {

        try {
            outputBuffer.write(formatter.format(aJCas));
            outputBuffer.flush();
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }

    @Override
    public void collectionProcessComplete() {
        Closeables.closeQuietly(outputBuffer);
    }
}
