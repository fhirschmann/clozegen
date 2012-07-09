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
package com.github.fhirschmann.clozegen.lib.examples;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.uimafit.factory.CollectionReaderFactory;

import com.github.fhirschmann.clozegen.lib.components.NGramWriter;
import com.github.fhirschmann.clozegen.lib.constraints.resources.ArticleConstraintResource;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.functions.LowerCaseFunction;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;

import de.tudarmstadt.ukp.dkpro.core.api.resources.DKProContext;
import de.tudarmstadt.ukp.dkpro.teaching.corpus.BrownCorpusReader;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * This example demonstrates the usage of {@link NGramWriter}.
 *
 * <p>
 * The brown_tei corpus is expected in $DKPRO_HOME.
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class NGramWriterExample {
    /**
     * Example 1.
     *
     * <p>
     * Writes preposition unigrams.
     * </p>
     *
     * @return a description
     * @throws ResourceInitializationException
     */
    public static AnalysisEngineDescription example1()
            throws ResourceInitializationException {
        // START SNIPPET: ngw1
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, 0,
                NGramWriter.PARAM_LOWER_CASE, true,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(PrepositionConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, "target/preposition-unigrams.txt");
        // END SNIPPET: ngw1
        return desc;
    }

    /**
     * Example 2.
     *
     * <p>
     * Writes preposition trigrams which occur at least 3 times.
     * </p>
     *
     * @return a description
     * @throws ResourceInitializationException
     */
    public static AnalysisEngineDescription example2()
            throws ResourceInitializationException {
        // START SNIPPET: ngw2
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, 1,
                NGramWriter.PARAM_MIN_FREQUENCY, 3,
                NGramWriter.PARAM_LOWER_CASE, true,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(ArticleConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, "target/article-trigrams.txt");
        // END SNIPPET: ngw2
        return desc;
    }

    /**
     * Example 3.
     *
     * <p>
     * Write n-grams consisting of articles and the word
     * preceeding the article which occur at least 3 times.
     * </p>
     *
     * @return a description
     * @throws ResourceInitializationException
     */
    public static AnalysisEngineDescription example3()
            throws ResourceInitializationException {
        // START SNIPPET: ngw3
        AnalysisEngineDescription desc = createPrimitiveDescription(
                NGramWriter.class,
                NGramWriter.PARAM_N, 1,
                NGramWriter.PARAM_INCLUDE_HEAD, false,
                NGramWriter.PARAM_LOWER_CASE, true,
                NGramWriter.PARAM_MIN_FREQUENCY, 3,
                NGramWriter.CONSTRAINT_KEY,
                createExternalResourceDescription(ArticleConstraintResource.class),
                NGramWriter.PARAM_OUTPUT_PATH, "target/article-bigrams.txt");
        // END SNIPPET: ngw3
        return desc;
    }

    /**
     * Runs all examples.
     *
     * @param args unused
     * @throws Exception on errors
     */
    public static void main(String[] args) throws Exception {
        CollectionReader cr = CollectionReaderFactory.createCollectionReader(
                BrownCorpusReader.class,
                BrownCorpusReader.PARAM_PATH,
                DKProContext.getContext().getWorkspace("brown_tei_small").getAbsolutePath(),
                BrownCorpusReader.PARAM_PATTERNS, new String[] {"[+]*.xml"});
        Pipeline pipeline = new Pipeline();
        pipeline.add(example1());
        pipeline.add(example2());
        pipeline.add(example3());
        pipeline.run(cr);
    }

}
