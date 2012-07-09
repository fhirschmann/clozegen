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
package com.github.fhirschmann.clozegen.lib.pipeline;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import static org.uimafit.pipeline.SimplePipeline.runPipeline;

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;

/**
 * A pipeline is a chain of AnalysisEngines arranged such that each step will be run
 * sequentially with all steps working on the same CAS.
 * <p>
 * This class provides convenience methods to add steps from engines, engine
 * descriptions and engine components dynamically.
 * </p>
 * An example scenario might look like this:
 * <pre>
 * JCas jCas = new JCasFactory.createJCas();
 * jCas.setDocumentText("This is test sentence. This is another sentence.");
 * jCas.setDocumentLanguage("en");
 * Pipeline steps = new Pipeline();
 * steps.add(StanfordSegmenter.class);
 * steps.run(jCas);
 * </pre>
 * </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Pipeline extends ForwardingList<AnalysisEngine> {
    /**
     * The list of steps elements.
     */
    private List<AnalysisEngine> steps;

    /**
     * Creates a new pipeline.
     */
    public Pipeline() {
        steps = Lists.newArrayList();
    }

    /**
     * Adds a step to the pipeline.
     *
     * @param step the step to be added
     * @return true if this pipeline has changed as a result of the call
     * @throws ResourceInitializationException on errors during initialization
     */
    public boolean add(final AnalysisEngineDescription step)
            throws ResourceInitializationException {
        AnalysisEngine engine;
        if (step.isPrimitive()) {
            engine = AnalysisEngineFactory.createPrimitive(step);
        } else {
            engine = AnalysisEngineFactory.createAggregate(step);
        }

        return steps.add(engine);
    }

    /**
     * Adds a step to the pipeline.
     *
     * @param step the step to be added
     * @return true if this pipeline has changed as a result of the call
     * @throws ResourceInitializationException on errors during initialization
     */
    public boolean add(final Class<? extends AnalysisComponent> step)
            throws ResourceInitializationException {

        return add((AnalysisEngineDescription) createPrimitiveDescription(step));
    }

    /**
     * Runs the pipeline.
     *
     * The pipeline will start at the given CAS.
     *
     * @param jCas the CAS to start the pipeline off with
     * @throws UIMAException on errors produced by UIMA
     * @throws IOException on IO errors
     */
    public void run(final JCas jCas)
            throws UIMAException, IOException {
        runPipeline(jCas, (AnalysisEngine[]) steps.toArray(
                new AnalysisEngine[steps.size()]));
    }

    /**
     * Runs the pipeline.
     *
     * The pipeline will start at the CAS constructed from the given {@code text}.
     *
     * @param text the text
     * @param languageCode the language of the text
     * @throws UIMAException on errors produced by UIMA
     * @throws IOException on IO errors
     */
    public void run(final String text, final String languageCode)
            throws UIMAException, IOException {
        final JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText(text);
        jcas.setDocumentLanguage(languageCode);
        run(jcas);
    }

    /**
     * Runs the pipeline.
     *
     * The pipeline will start at the given CollectionReader.
     *
     * @param reader the Collection Reader to start the pipeline off with
     * @throws UIMAException when UIMA errors occur
     * @throws IOException when errors occur while reading from a file
     */
    public void run(final CollectionReader reader)
            throws UIMAException, IOException {
        runPipeline(reader, (AnalysisEngine[]) steps.toArray(
                new AnalysisEngine[steps.size()]));
    }

    @Override
    public String toString() {
        final ToStringHelper str = Objects.toStringHelper(this);
        // UIMA's AnalysisEngine has no useful toString() method
        for (AnalysisEngine a : steps) {
            str.addValue(a.getAnalysisEngineMetaData().getName());
        }
        return str.toString();
    }

    @Override
    protected List<AnalysisEngine> delegate() {
        return steps;
    }
}
