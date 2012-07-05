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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import com.github.fhirschmann.clozegen.lib.constraints.CoveredTextConstraint;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * This is an extension to {@link TypeConstraintResource} which additionally matches
 * an annotation's covered text using {@link CoveredTextConstraint}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CoveredTextConstraintResource extends TypeConstraintResource {
    /** The string to match. */
    public static final String PARAM_STRING = "String";
    @ConfigurationParameter(name = PARAM_STRING, mandatory = true)
    private String string;

    @Override
    public FSMatchConstraint getConstraint(final JCas jcas) {
        FSMatchConstraint typeConstraint = super.getConstraint(jcas);
        return ConstraintFactory.instance().and(typeConstraint,
                new CoveredTextConstraint(string));
    }
}
