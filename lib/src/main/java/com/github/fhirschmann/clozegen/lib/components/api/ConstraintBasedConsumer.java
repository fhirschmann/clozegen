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

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ExternalResource;

import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintProvider;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * A consumer which calls {@link GapProcessor#process(JCas, List, int)} for each
 * word matched by a given constraint.
 *
 * <p>
 * Due to the lack of mixins or multiple inheritance in the Java language, this
 * is the same as {@link ConstraintBasedAnnotator}.
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class ConstraintBasedConsumer extends JCasConsumer_ImplBase implements
        ConstraintProvider, GapProcessor {
    /**
     * <em>[mandatory]</em>
     *
     * A constraint which limits the words {@link GapProcessor#process(JCas, List, int)}
     * is called for.
     *
     * @see com.github.fhirschmann.clozegen.lib.constraints
     */
    public static final String CONSTRAINT_KEY = "Constraint";
    @ExternalResource(key = CONSTRAINT_KEY, mandatory = true)
    private ConstraintProvider constraint;

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        UIMAUtils.annotationCaller(aJCas, getConstraint(aJCas), this);
    }

    @Override
    public FSMatchConstraint getConstraint(final JCas jcas) {
        return constraint.getConstraint(jcas);
    }
}
