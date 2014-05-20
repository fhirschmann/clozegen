/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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

import static com.google.common.base.Preconditions.checkArgument;

import java.net.URL;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import com.github.fhirschmann.clozegen.lib.register.api.Register;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;

/**
 * A register of mappings from file extensions to {@link WriterRegisterEntry}.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class WriterRegister extends Register<WriterRegisterEntry> {
    /**
     * Returns the output writer associated with the given {@code output} file.
     *
     * @param output the output file
     * @return a new output writer
     * @throws ResourceInitializationException on errors during initialization
     */
    public AnalysisEngineDescription getWriterFor(final URL output)
            throws ResourceInitializationException {
        String outExt = MiscUtils.getFileExtension(output.getFile());
        checkArgument(register.containsKey(
                outExt), "Unknown output file type: " + outExt);
        return register.get(outExt).getWriterDescription(output.getFile());
    }
}
