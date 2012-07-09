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
import static com.google.common.base.Objects.firstNonNull;

import org.apache.uima.resource.ResourceInitializationException;

/**
 * This class provides singleton instances of the available Registers.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Registers {
    private static AnnotatorRegister annotator;
    private static ReaderRegister reader;
    private static WriterRegister writer;

    /**
     * Returns the annotator register. This register holds all information
     * used to construct an analysis engine in order to generate gaps.
     *
     * @return the annotator Register
     * @throws ResourceInitializationException on errors during initialization
     */
    public static AnnotatorRegister annotator() throws ResourceInitializationException {
        annotator = firstNonNull(annotator,
                RegisterFactory.createDefaultAnnotatorRegister());
        return annotator;
    }

    /**
     * Returns the writer register. This register holds all information
     * used to construct a consumer which writes the cloze test to a file.
     *
     * @return the writer register
     * @throws ResourceInitializationException on errors during initialization
     */
    public static WriterRegister writer() {
        writer = firstNonNull(writer,
                RegisterFactory.createDefaultWriterRegister());
        return writer;
    }

    /**
     * Returns the reader register. This register maps file extensions to UIMA
     * reader. You can safely add new mappings to this register.
     *
     * @return the readerRegister
     */
    public static ReaderRegister reader() {
        reader = firstNonNull(reader,
                RegisterFactory.createDefaultReaderRegister());
        return reader;
    }
}
