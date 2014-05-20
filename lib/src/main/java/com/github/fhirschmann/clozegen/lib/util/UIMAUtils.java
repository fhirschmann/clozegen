/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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
package com.github.fhirschmann.clozegen.lib.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.uima.UIMAException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.NonEmptyStringList;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.CollectionReaderFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

import com.github.fhirschmann.clozegen.lib.components.api.GapProcessor;
import com.github.fhirschmann.clozegen.lib.functions.CoveredTextFunction;
import com.github.fhirschmann.clozegen.lib.functions.EscapeNullFunction;
import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.teaching.corpus.BrownCorpusReader;

/**
 * Utility functions for UIMA related stuff.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public final class UIMAUtils {
    /**
     * Utility class.
     */
    private UIMAUtils() {
    }

    /**
     * Returns true if and only if there exists an annotation in the given
     * {@code jcas} having the same type and bounds as {@code annotaton}.
     *
     * @param <T> the annotation type
     * @param jcas the JCas
     * @param annotation the annotation in question
     * @return true if there exists a similar annotation
     */
    public static <T extends Annotation> boolean hasSimilarAnnotation(
            final JCas jcas, final T annotation) {
        for (Annotation subject : JCasUtil.select(jcas, annotation.getClass())) {
            if (!subject.equals(annotation)
                    && (subject.getBegin() == annotation.getBegin())
                    && (subject.getEnd() == annotation.getEnd())) {
                return true;
            }
        }

        return false;

    }

    /**
     * Creates a {@link GapAnnotation} from {@code validAnswers} and
     * {@code invalidAnswers}. The resulting list of all answer options
     * of a gap will be composed of union of {@code validAnswers} and
     * {@code invalidAnswers} in random order.
     *
     * @param aJCas The {@link JCas} this Annotation belongs to
     * @param validAnswers the valid answers for this gap
     * @param invalidAnswers the invalid answers for this gap
     * @return a new {@link GapAnnotation}
     */
    public static GapAnnotation createGapAnnotation(final JCas aJCas,
            final Set<String> validAnswers, final Set<String> invalidAnswers) {
        final GapAnnotation annotation = new GapAnnotation(aJCas);

        final List<String> allAnswers = Lists.newArrayList(
                Sets.union(invalidAnswers, validAnswers));
        Collections.shuffle(allAnswers);

        final NonEmptyStringList all = (NonEmptyStringList)
                FSCollectionFactory.createStringList(
                aJCas, allAnswers);
        annotation.setAllAnswers(all);

        final NonEmptyStringList valid = (NonEmptyStringList)
                FSCollectionFactory.createStringList(
                aJCas, validAnswers);
        annotation.setValidAnswers(valid);

        return annotation;
    }

    /**
     * Creates a {@link GapAnnotation} from a {@link Gap}.
     *
     * @param aJCas The {@link JCas} this Annotation belongs to
     * @param gap the gap to create the annotation for
     * @see UIMAUtils#createGapAnnotation(JCas, Set, Set)
     * @return a new {@link GapAnnotation}
     */
    public static GapAnnotation createGapAnnotation(final JCas aJCas, final Gap gap) {
        return createGapAnnotation(aJCas, gap.getValidAnswers(), gap.getInvalidAnswers());
    }

    /**
     * Creates a {@link Gap} from a {@link GapAnnotation}.
     *
     * @param annotation the annotation the generate the gap from
     * @return a gap based on the input annotation
     */
    public static Gap createGap(final GapAnnotation annotation) {
        List<String> valid = Lists.newArrayList(
                FSCollectionFactory.create(annotation.getValidAnswers()));
        List<String> invalid = Lists.newArrayList(
                FSCollectionFactory.create(annotation.getAllAnswers()));
        invalid.removeAll(valid);

        return Gap.with(valid, invalid);
    }

    /**
     * Copy the bounds ({@link Annotation#getBegin()} and {@link Annotation#getEnd()})
     * from the {@code source} annotation to the {@code destination} annotation.
     * annotation.
     *
     * @param source the source annotation
     * @param destination the destination annotation
     */
    public static void copyBounds(final Annotation source, final Annotation destination) {
        destination.setBegin(source.getBegin());
        destination.setEnd(source.getEnd());
    }

    /**
     * Returns a List of {@code T} of the {@code num} neighbors.
     *
     * @param <T> the annotation type
     * @param clazz the class of the annotation type
     * @param annotationList the list of annotation
     * @param offset the offset of the element in {@code annotationList}
     * @param num the number of neighbors to receive
     * @return a list of neighbors
     */
    public static <T extends Annotation> List<T> getAdjacentAnnotations(
            final Class<T> clazz, final List<Annotation> annotationList,
            final int offset, final int num) {
        // Get a list of all T tags
        final List<T> tList = Lists.newArrayList(
                Iterables.filter(annotationList, clazz));

        // Get the neighbors of our T tag
        final List<T> adjacent = CollectionUtils.getNullPaddedAdjacentTo(
                tList, tList.indexOf(annotationList.get(offset)), num);

        return adjacent;
    }

    /**
     * Returns the covered text of the list of {@link Annotation}s produced
     * by {@link UIMAUtils#getAdjacentAnnotations(Class, List, int, int)}.
     * {@code null} will be replaced with "NULL" by {@link EscapeNullFunction}.
     *
     * @param <T> the annotation type
     * @param clazz the class of the annotation type
     * @param annotationList the list of annotation
     * @param offset the offset of the element in {@code annotationList}
     * @param num the number of neighbors to receive
     * @return a list of neighbors (covered text)
     */
    public static <T extends Annotation> List<String> getAdjacentTokens(
            final Class<T> clazz, final List<Annotation> annotationList,
            final int offset, final int num) {

        final List<T> adjacent = getAdjacentAnnotations(
                clazz, annotationList, offset, num);

        final List<String> tokens = Lists.newArrayList(Collections2.transform(
                adjacent, Functions.compose(
                new EscapeNullFunction(), new CoveredTextFunction())));

        return tokens;
    }

    /**
     * Calls {@code processor} for each {@link Annotation} matched by
     * {@code constraint} in a sentence.
     *
     * @param jcas the JCas to work on
     * @param constraint the constraint to match
     * @param processor the callback
     */
    public static void annotationCaller(final JCas jcas,
            final FSMatchConstraint constraint, final GapProcessor processor) {
        for (Sentence sentence : JCasUtil.select(jcas, Sentence.class)) {
            int i = 0;
            List<Annotation> alist = JCasUtil.selectCovered(Annotation.class, sentence);
            for (Annotation annotation : alist) {
                if ((checkNotNull(constraint).match(annotation))) {
                    processor.process(jcas, alist, i);
                }
                i++;
            }
        }
    }

    /**
     * Creates a JCas.
     *
     * @param text the document text of the JCas
     * @param languageCode the language code of the {@code text}
     * @return a sample jcas
     * @throws UIMAException on errors creating the JCas
     */
    public static JCas createJCas(final String text, final String languageCode)
            throws UIMAException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentLanguage(languageCode);
        jcas.setDocumentText(text);

        DocumentMetaData metadata = DocumentMetaData.create(jcas);
        metadata.setDocumentId("test");
        return jcas;
    }

    /**
     * Creates a new {@link CollectionReader} solely meant for testing purposes.
     *
     * @return a new {@link CollectionReader}
     * @throws ResourceInitializationException on errors during initialization
     */
    public static CollectionReader createTestReader()
            throws ResourceInitializationException {
        return CollectionReaderFactory.createCollectionReader(
                BrownCorpusReader.class,
                BrownCorpusReader.PARAM_PATH,
                Resources.getResource("brown_tei_test").getPath(),
                BrownCorpusReader.PARAM_PATTERNS, new String[] {"[+]*.xml"});
    }
}
