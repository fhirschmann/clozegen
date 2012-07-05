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
package com.github.fhirschmann.clozegen.lib.register;

import com.github.fhirschmann.clozegen.lib.components.api.OutputFileWriter;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class WriterRegisterEntry extends DescriptionRegisterEntry {
    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     */
    public WriterRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass) {
        super(identifier, componentClass);
    }

    /**
     * Creates a new register entry.
     *
     * @param identifier the identifier of this entry
     * @param componentClass the component class for the primitive description
     * @param configurationData the configuration data for the primitive description
     */
    public WriterRegisterEntry(final String identifier,
            final Class<? extends AnalysisComponent> componentClass,
            final Object... configurationData) {
        super(identifier, componentClass, configurationData);
    }

    /**
     * Creates a new writer description which will write to {@code outputFile}.
     *
     * @param outputFile the file to write to
     * @return a new writer description
     * @throws ResourceInitializationException on errors during initialization
     */
    public AnalysisEngineDescription getWriterDescription(final String outputFile)
            throws ResourceInitializationException {
        return getDescription(OutputFileWriter.PARAM_OUTPUT_FILE,
                checkNotNull(outputFile));
    }
}
