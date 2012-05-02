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

import com.github.fhirschmann.clozegen.lib.type.Distractor;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.util.FSCollectionFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class DistractorAnnotator extends JCasAnnotator_ImplBase {
    public abstract int getType();
    public abstract List<String> generate(Annotation subject);

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        for (Iterator<Annotation> i = jcas.getAnnotationIndex(getType()).iterator(); i.hasNext();) {
            Annotation subject = i.next();
            Distractor distractor = new Distractor(jcas);

            distractor.setBegin(subject.getBegin());
            distractor.setEnd(subject.getEnd());

            Collection<String> distractors = generate(subject);
            NonEmptyStringList l = (NonEmptyStringList)FSCollectionFactory.createStringList(jcas, distractors);

            distractor.setDistractors(l);
            distractor.addToIndexes();

        }
    }
}
