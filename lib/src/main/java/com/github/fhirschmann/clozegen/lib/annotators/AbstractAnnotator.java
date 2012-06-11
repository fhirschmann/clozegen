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
package com.github.fhirschmann.clozegen.lib.annotators;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import java.util.List;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class AbstractAnnotator extends JCasAnnotator_ImplBase {
    /**
     * Should return the {@link FSMatchConstraint} which matches annotations
     * for which {@link AbstractAnnotator#process(java.util.List, int)} should
     * be called.
     *
     * @return a constraint
     */
    public abstract FSMatchConstraint getConstraint();

    /**
     * Called for each annotation which is matched by
     * {@link AbstractAnnotator#getConstraint()}.
     *
     * @param jcas the JCas
     * @param annotationList the list of annotations in a sentence
     * @param index the index of the annotation in question
     */
    public abstract void process(final JCas jcas, List<Annotation> annotationList,
            int index);

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            int i = 0;
            List<Annotation> alist = JCasUtil.selectCovered(Annotation.class, sentence);
            for (Annotation annotation : alist) {
                if ((getConstraint() == null) || (getConstraint().match(annotation))) {
                    process(aJCas, alist, i);
                }
                i++;
            }
        }
    }
}
