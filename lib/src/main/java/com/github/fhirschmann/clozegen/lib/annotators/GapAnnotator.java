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

import com.github.fhirschmann.clozegen.lib.generator.Gap;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import java.util.List;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSTypeConstraint;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ExternalResource;
import org.uimafit.util.JCasUtil;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class GapAnnotator extends JCasAnnotator_ImplBase {
    public final static String GAP_ANNOTATOR_INTERFACE = "GapAnnotatorInterface";
    @ExternalResource(key = GAP_ANNOTATOR_INTERFACE)
    private GapAnnotatorInterface annotatorInterface;

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        // Throw an exception if the language is not supported.
        Gap gap;
        GapAnnotation gapAnnotation;
        FSTypeConstraint constraint = annotatorInterface.getConstraint();

        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            int i = 0;
            List<Annotation> alist = JCasUtil.selectCovered(Annotation.class, sentence);
            for (Annotation annotation : alist) {
                if ((constraint == null) || (constraint.match(annotation))) {
                    gap = annotatorInterface.generator(alist, i).generate(4);
                    gapAnnotation = UIMAUtils.createGapAnnotation(aJCas, gap);
                    UIMAUtils.copyBounds(annotation, gapAnnotation);
                    gapAnnotation.addToIndexes();
                }
                i++;
            }
        }
    }

}
