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
package com.github.fhirschmann.clozegen.lib.adapters.api;

import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import java.util.List;
import org.apache.uima.jcas.tcas.Annotation;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintProvider;

/**
 * Adapters implementing this interface will provide an instance of a gap
 * generator.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public interface GeneratorAdapter {
    /**
     * This method gets called for each word in a sentence which matches
     * {@link ConstraintProvider#getConstraint(org.apache.uima.jcas.JCas)}.
     *
     * @param annotationList the list of annotations in the current sentence
     * @param offset the offset (index) of the word to generate a gap for
     * @return a gap generator
     */
    GapGenerator generator(List<Annotation> annotationList, int offset);
}
