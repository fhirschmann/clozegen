/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.component.api;

import com.github.fhirschmann.clozegen.lib.constraint.api.ConstraintProvider;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ExternalResource;

/**
 * This is equal to {@link AbstractAnnotator}, except that this class extends
 * {@link JCasConsumer_ImplBase} instead of {@link JCasAnnotator_ImplBase}.
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
     * @see com.github.fhirschmann.clozegen.lib.constraint
     */
    public static final String CONSTRAINT_KEY = "Constraint";
    @ExternalResource(key = CONSTRAINT_KEY, mandatory = true)
    private ConstraintProvider constraint;

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        UIMAUtils.annotationCaller(aJCas, getConstraint(), this);
    }

    @Override
    public FSMatchConstraint getConstraint() {
        return constraint.getConstraint();
    }
}
