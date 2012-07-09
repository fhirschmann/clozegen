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
package com.github.fhirschmann.clozegen.lib.constraints;

import java.util.Collection;

import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * A constraint which tries to match an annotation's covered text with any
 * element of the given set.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
@SuppressWarnings("serial")
public class CoveredTextConstraint implements FSMatchConstraint {
    /** The covered text to match. */
    private Collection<String> matchText;

    /**
     * Constructs a new constraint.
     *
     * @param coveredText covered text to match
     */
    public CoveredTextConstraint(final Collection<String> coveredText) {
        this.matchText = coveredText;
    }

    @Override
    public boolean match(final FeatureStructure fs) {
        if (fs instanceof Annotation) {
            Annotation annotation = (Annotation) fs;
            return matchText.contains(annotation.getCoveredText());
        } else {
            return false;
        }
    }
}
