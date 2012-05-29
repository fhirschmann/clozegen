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
package com.github.fhirschmann.clozegen.lib.imf;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * Writes all {@link GapAnnotation} to a file using the intermediate format.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class IntermediateFormatWriter extends JCasConsumer_ImplBase {
    /** The output file to write to. */
    public static final String PARAM_OUTPUT_FILE = "OutputFile";
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    private String outputFile;

    private BufferedWriter outputBuffer;

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
        String imf = IntermediateFormat.format(aJCas);
        try {
            outputBuffer.write(String.format("%s%n", imf));
            outputBuffer.flush();
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }

    @Override
    public void collectionProcessComplete() {
        try {
            outputBuffer.close();
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }
}
