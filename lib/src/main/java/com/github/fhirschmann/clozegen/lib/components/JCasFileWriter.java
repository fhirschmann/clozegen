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
