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

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;

import com.github.fhirschmann.clozegen.lib.register.api.Register;
import com.github.fhirschmann.clozegen.lib.util.MiscUtils;

/**
 * A register of mappings from file extensions to {@link ReaderRegisterEntry}.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class ReaderRegister extends Register<ReaderRegisterEntry> {
    /**
     * Returns the collection reader associated with the given input file.
     *
     * @param input the input file
     * @param languageCode the language of the input file
     * @throws ResourceInitializationException on errors during initialization
     * @return a new {@link CollectionReader}
     */
    public CollectionReader getReaderForFile(final URL input,
            final String languageCode) throws ResourceInitializationException {
        String inExt = MiscUtils.getFileExtension(input.getFile());
        checkArgument(register.containsKey(inExt), "Unknown input file type: " + inExt);
        return get(inExt).getReader(input, languageCode);
    }
}
