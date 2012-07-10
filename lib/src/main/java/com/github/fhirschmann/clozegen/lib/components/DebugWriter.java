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
package com.github.fhirschmann.clozegen.lib.components;

import java.util.Iterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.util.FSCollectionFactory;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;

/**
 * Logs a {@link JCas} (document) by displaying its token's types, names, the text
 * covered, and in case of GapAnnotations, the valid and invalid answer options.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DebugWriter extends JCasConsumer_ImplBase {

    @Override
    public final void process(final JCas jCas) throws AnalysisEngineProcessException {
        final StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s%n", jCas.getDocumentText()));

        for (Iterator<Annotation> i = jCas.
                getAnnotationIndex().iterator(); i.hasNext();) {
            final Annotation annotation = i.next();
            sb.append(String.format("[%s] (%s,%s) %s",
                    annotation.getType().getShortName(),
                    annotation.getBegin(),
                    annotation.getEnd(),
                    annotation.getCoveredText()));
            if (annotation instanceof GapAnnotation) {
                final GapAnnotation gapAnnotation = (GapAnnotation) annotation;
                sb.append(String.format(" %s%s%n",
                        FSCollectionFactory.create(
                        gapAnnotation.getValidAnswers()).toString(),
                        FSCollectionFactory.create(
                        gapAnnotation.getAllAnswers()).toString()));
            } else {
                if (annotation instanceof POS) {
                    final POS pos = (POS) annotation;
                    sb.append(String.format(" [%s]", pos.getPosValue()));
                }
                sb.append(String.format("%n"));
            }
        }
        getLogger().info(sb.toString());
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        return str.toString();
    }
}
