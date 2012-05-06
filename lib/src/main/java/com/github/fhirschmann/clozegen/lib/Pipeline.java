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
package com.github.fhirschmann.clozegen.lib;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import java.io.IOException;
import java.util.ArrayList;
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

/**
 * A steps is a chain of AnalysisEngines arranged so that each step will be run
 * sequentially with all steps steps working on the same CAS. <p> This class provides
 * convenience methods to add steps steps from engines, engine descriptions and engine
 * components dynamically. </p> <p> An example scenario might look like this:
 * <pre>
 * JCas jCas = new JCasFactory.createJCas();
 * jCas.setDocumentText("This is test sentence. This is another sentence.");
 * jCas.setDocumentLanguage("en");
 * Pipeline steps = new Pipeline();
 * steps.addStep(StanfordSegmenter.class);
 * steps.run(jCas);
 * </pre> </p>
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class Pipeline {

    /**
     * The list of steps elements.
     */
    private ArrayList<AnalysisEngine> steps = new ArrayList<AnalysisEngine>();

    /**
     * Adds a step to the steps.
     *
     * @param step the steps step to be added
     */
    public final void addStep(final AnalysisEngine step) {
        steps.add(step);
    }

    /**
     * Adds a step to the steps.
     *
     * @param step the steps step to be added
     * @throws ResourceInitializationException on errors during initialization
     */
    public final void addStep(final AnalysisEngineDescription step)
            throws ResourceInitializationException {
        AnalysisEngine en;
        if (step.isPrimitive()) {
            en = AnalysisEngineFactory.createPrimitive(step);
        } else {
            en = AnalysisEngineFactory.createAggregate(step);
        }

        steps.add(en);
    }

    /**
     * Adds a step to the steps.
     *
     * @param step the steps step to be added
     * @throws ResourceInitializationException on errors during initialization
     */
    public final void addStep(final Class<? extends AnalysisComponent> step)
            throws ResourceInitializationException {

        addStep((AnalysisEngineDescription) createPrimitiveDescription(step));
    }

    /**
     * Runs the steps.
     *
     * The steps will start at the given CAS.
     *
     * @param jCas the CAS to start the steps off with
     * @throws UIMAException on errors produced by UIMA
     * @throws IOException on errors when reading a file
     */
    public final void run(final JCas jCas)
            throws UIMAException, IOException {
        runPipeline(jCas, (AnalysisEngine[]) steps.toArray(
                new AnalysisEngine[0]));
    }

    /**
     * Runs the steps.
     *
     * The steps will start at the given CollectionReader.
     *
     * @param reader the Collection Reader to start the steps off with
     * @throws UIMAException when UIMA errors occur
     * @throws IOException when errors occur while reading from a file
     */
    public final void run(final CollectionReader reader)
            throws UIMAException, IOException {
        runPipeline(reader, (AnalysisEngine[]) steps.toArray(
                new AnalysisEngine[0]));
    }

    @Override
    public final String toString() {
        ToStringHelper ts = Objects.toStringHelper(this);
        // UIMA's AnalysisEngine has no useful toString() method
        for (AnalysisEngine a : steps) {
            ts.addValue(a.getAnalysisEngineMetaData().getName());
        }
        return ts.toString();
    }
}