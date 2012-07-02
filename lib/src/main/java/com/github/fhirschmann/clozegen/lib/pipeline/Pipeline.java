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
package com.github.fhirschmann.clozegen.lib.pipeline;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;
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
import static org.uimafit.pipeline.SimplePipeline.runPipeline;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;
import org.uimafit.factory.JCasFactory;

/**
 * A pipeline is a chain of AnalysisEngines arranged such that each step will be run
 * sequentially with all steps working on the same CAS.
 * <p>
 * This class provides convenience methods to add steps from engines, engine
 * descriptions and engine components dynamically.
 * </p>
 * <p>
 * You can add as many steps as you like, however, you should not remove
 * any steps from this pipeline, because the persistence mechanism might then
 * fail.
 * <p>
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
     * The index of the steps which should survive a reset.
     */
    private List<Integer> persistent;

    /**
     * Creates a new pipeline.
     */
    public Pipeline() {
        steps = Lists.newArrayList();
        persistent = Lists.newArrayList();
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
     * @return the indices of persistent steps
     */
    public List<Integer> getPersistent() {
        return persistent;
    }

    /**
     * @param persistent the indices of persistent steps
     */
    public void setPersistent(final List<Integer> persistent) {
        this.persistent = persistent;
    }

    /**
     * Marks the step with index {@code index} as persistent.
     *
     * @param index the index
     */
    public void markPersistent(final int index) {
        persistent.add(index);
    }

    /**
     * Marks the most recently added step as persistent.
     */
    public void markPersistent() {
        persistent.add(steps.size() - 1);
    }

    /**
     * Resets this pipeline.
     *
     * <p>
     * All persistent steps will survive the reset.
     * </p>
     */
    public void reset() {
        List<AnalysisEngine> newlist = Lists.newArrayList();

        for (Integer index : persistent) {
            newlist.add(steps.get(index.intValue()));
        }

        steps = newlist;
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
