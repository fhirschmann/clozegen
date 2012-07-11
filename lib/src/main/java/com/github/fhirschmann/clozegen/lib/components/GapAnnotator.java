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

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.descriptor.ExternalResource;

import com.github.fhirschmann.clozegen.lib.adapters.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.components.api.ConstraintBasedAnnotator;
import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Optional;

/**
 * This annotator annotates words with a {@link GapAnnotation}.
 *
 * @see com.github.fhirschmann.clozegen.lib.examples
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class GapAnnotator extends ConstraintBasedAnnotator {
    /**
     * <em>[mandatory]</em>
     *
     * The argument to this keyword should be a {@link GeneratorAdapter} which
     * knows how to create a {@link GapGenerator}.
     */
    public static final String ADAPTER_KEY = "Adapter";
    @ExternalResource(key = ADAPTER_KEY)
    private GeneratorAdapter adapter;

    /**
     * The default number of answers to generate.
     */
    public static final int DEFAULT_ANSWER_COUNT = 4;

    /**
     * <em>[optional]</em>
     *
     * The number of invalid answers to generate. This argument is optional
     * and defaults to 4.
     *
     * <p>
     * Please note that it is up to the underlying generator to respect this
     * parameter.
     * </p>
     */
    public static final String PARAM_ANSWER_COUNT = "AnswerCount";
    @ConfigurationParameter(name = PARAM_ANSWER_COUNT,
            mandatory = false, defaultValue = "" + DEFAULT_ANSWER_COUNT)
    private int answerCount;

    @Override
    public void process(final JCas jcas, final List<Annotation> annotationList,
            final int index) {
        getLogger().info(String.format("Generating %d options for \"%s\"", answerCount,
                annotationList.get(index).getCoveredText()));
        Optional<Gap> gap = adapter.generator(annotationList, index).
                generate(answerCount);

        if (gap.isPresent()) {
            jcas.getAnnotationIndex(GapAnnotation.type);
            GapAnnotation gapAnnotation = UIMAUtils.createGapAnnotation(jcas, gap.get());
            UIMAUtils.copyBounds(annotationList.get(index), gapAnnotation);
            if (!UIMAUtils.hasSimilarAnnotation(jcas, gapAnnotation)) {
                gapAnnotation.addToIndexes();
            } else {
                getLogger().warn("Similar gap not added: " + gap.get().toString());
            }
        }
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        str.add("answerCount", answerCount);
        str.add("adapter", adapter);
        return str.toString();
    }
}
