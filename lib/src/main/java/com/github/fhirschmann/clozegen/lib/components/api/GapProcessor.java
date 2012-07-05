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
package com.github.fhirschmann.clozegen.lib.components.api;

import java.util.List;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintProvider;

/**
 * A Gap Processor is a class which processes gaps. This can be, for example,
 * an annotator which generates gaps or a writer which collects frequency data.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public interface GapProcessor {
    /**
     * Called for each annotation which is matched by
     * {@link ConstraintProvider#getConstraint(JCas)}.
     *
     * @param jcas the JCas
     * @param annotationList the list of annotations in a sentence
     * @param index the index of the annotation in question
     */
    void process(final JCas jcas, List<Annotation> annotationList, int index);
}
